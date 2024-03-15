package org.kpaas.sidecar.portal.api.controller;

import org.cloudfoundry.client.v3.builds.CreateBuildResponse;
import org.kpaas.sidecar.portal.api.model.Application;
import org.kpaas.sidecar.portal.api.model.Route;
import org.kpaas.sidecar.portal.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;

public class SidecarController {

    // build process interval time (sec)
    private final long BUILD_INTERVAL_SECOND = 300;

    /*
    * 앱 배포 과정
    1. JAVA 내부에서 사용할 임시 파일을 생성
    2. APP 생성
    3. ROUTE 생성
    4. APP - ROUTE 맵핑
    5. 패키지 생성
    6. 패키지에 임시 파일을 업로드
    7. 빌드 생성
    8. 앱 시작
    */

    @Autowired
    private ApplicationsServiceV3 appServiceV3;

    @Autowired
    private RoutesServiceV3 routesServiceV3;

    @Autowired
    private PackagesService packagesService;

    @Autowired
    private BuildsService buildsService;

    @Autowired
    private DropletsService dropletsService;

    String appGuid;
    String routeGuid;
    String packageGuid;
    String buildGuid;
    String dropletGuid;

    public void push(@RequestBody Application app, @RequestBody Route route) throws Exception {
        // 1. JAVA 내부에서 사용할 임시 파일을 생성 - 생략
        File file = null;

        // 2. App 생성
        //Application app = null;
        appGuid = appServiceV3.create(app).getId();

        // 3. ROUTE 생성
        //Route route = null;
        routeGuid = routesServiceV3.create(route).getId();

        // 4. APP - ROUTE 맵핑
        routesServiceV3.insertDestinations(routeGuid, appGuid);

        // 5. 패키지 생성
        packageGuid = packagesService.create(appGuid).getId();

        // 6. 패키지에 임시 파일을 업로드

        packagesService.upload(packageGuid, file);

        // 7. 빌드 생성
        Thread th = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            CreateBuildResponse createBuildResponse = buildsService.create(packageGuid);
                            buildGuid = createBuildResponse.getId();
                            dropletGuid = createBuildResponse.getDroplet().getId();

                            //현재 시각
                            long start = System.currentTimeMillis();

                            //종료 시각
                            long end = start + BUILD_INTERVAL_SECOND *1000;

                            // 빌드 확인 중 = STAGED
                            while(true){
                                if( buildsService.get(buildGuid).getState().getValue().equals("STAGED") ) {
                                    break;
                                }
                                if ( System.currentTimeMillis() > end ){
                                    throw new Exception("App Build Time Over");
                                }
                                Thread.sleep(1000);
                            }

                            appServiceV3.setCurrentDroplet(appGuid, dropletGuid);
                            //앱 실행버튼이 on일때
                            if (true) {
                                // 8. 앱 시작
                                appServiceV3.start(appGuid);
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
        );
        th.start();
    }
}
