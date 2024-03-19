apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
  name: ${rbName}
  namespace: ${nsName}
  annotations:
    cloudfoundry.org/propagate-cf-role: "true"
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: korifi-controllers-admin
subjects:
  - kind: ServiceAccount
    name: ${userName}
    namespace: ${nsName}
