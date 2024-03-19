apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
  name: ${rbName}
  namespace: ${nsName}
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: korifi-controllers-root-namespace-user
subjects:
  - kind: ServiceAccount
    name: ${userName}
    namespace: ${nsName}
