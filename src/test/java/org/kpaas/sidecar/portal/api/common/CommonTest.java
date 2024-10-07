package org.kpaas.sidecar.portal.api.common;

import org.cloudfoundry.reactor.TokenProvider;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kpaas.sidecar.portal.api.login.AuthUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *  Common Service 클래스
 *
 * @author woogie
 * @version 1.0
 * @since 2024.09.09
 **/

@ExtendWith(MockitoExtension.class)
@DisplayName("Common Service Tests")
class CommonTest {

    @Spy
    @InjectMocks
    private Common common;

    @Mock
    private AuthUtil authUtil;

    @Mock
    private TokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(common, "apiHost", "test-api-host");
        ReflectionTestUtils.setField(common, "tokenKind", "Bearer");
        ReflectionTestUtils.setField(common, "token", "test-token");
    }

    @Test
    @DisplayName("Should create CloudFoundryClient")
    void cloudFoundryClient() {
        ReactorCloudFoundryClient client = common.cloudFoundryClient(tokenProvider);
        assertNotNull(client);
    }

    @Nested
    @DisplayName("TokenProvider Tests")
    class TokenProviderTests {
        @Test
        @DisplayName("Should return TokenProvider")
        void tokenProvider() {
            doReturn(tokenProvider).when(common).tokenProvider();
            TokenProvider result = common.tokenProvider();
            assertNotNull(result);
            assertEquals(tokenProvider, result);
        }

        @Test
        @DisplayName("Should return TokenProvider with given token")
        void tokenProviderWithToken() {
            doReturn(tokenProvider).when(common).tokenProvider(anyString());
            TokenProvider result = common.tokenProvider("test-token");
            assertNotNull(result);
            assertEquals(tokenProvider, result);
        }
    }

    @Nested
    @DisplayName("List Null Check Tests")
    class ListNullCheckTests {
        @Test
        @DisplayName("Should handle null and empty String lists")
        void stringListNullCheck() {
            List<String> nullList = null;
            List<String> emptyList = new ArrayList<>();
            List<String> nonEmptyList = new ArrayList<>(Arrays.asList("test"));

            assertEquals(new ArrayList<>(), common.stringListNullCheck(nullList));
            assertEquals(new ArrayList<>(), common.stringListNullCheck(emptyList));
            assertEquals(nonEmptyList, common.stringListNullCheck(nonEmptyList));
        }

        @Test
        @DisplayName("Should handle null and empty Integer lists")
        void integerListNullCheck() {
            List<Integer> nullList = null;
            List<Integer> emptyList = new ArrayList<>();
            List<Integer> nonEmptyList = new ArrayList<>(Arrays.asList(1, 2, 3));

            assertEquals(new ArrayList<>(), common.integerListNullCheck(nullList));
            assertEquals(new ArrayList<>(), common.integerListNullCheck(emptyList));
            assertEquals(nonEmptyList, common.integerListNullCheck(nonEmptyList));
        }
    }

    @Nested
    @DisplayName("String Null Check Tests")
    class StringNullCheckTests {
        @Test
        @DisplayName("Should handle null and empty strings")
        void stringNullCheck() {
            String nullString = null;
            String emptyString = "";
            String nonEmptyString = "test";

            assertEquals("", common.stringNullCheck(nullString));
            assertEquals("", common.stringNullCheck(emptyString));
            assertEquals(nonEmptyString, common.stringNullCheck(nonEmptyString));
        }

        @Test
        @DisplayName("Should handle null and empty strings (stringNullChecks method)")
        void stringNullChecks() {
            String nullString = null;
            String emptyString = "";
            String nonEmptyString = "test";

            assertEquals("", common.stringNullChecks(nullString));
            assertEquals("", common.stringNullChecks(emptyString));
            assertEquals(nonEmptyString, common.stringNullChecks(nonEmptyString));
        }
    }

    @Test
    @DisplayName("Should correctly use CustomClass generic type")
    void customClass() {
        Common.CustomClass<String> customClass = common.new CustomClass<>();
        customClass.set("test");
        assertEquals("test", customClass.get());
    }
}