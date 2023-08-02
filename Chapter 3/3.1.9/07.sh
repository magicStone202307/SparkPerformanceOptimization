#使用 OpenSSL 生成一个 2048 位的私钥文件 dashboard.key
openssl genrsa -out dashboard.key 2048
#使用 OpenSSL 生成一个证书签名请求文件 dashboard.csr，同时使用 dashboard.key 作为私钥，并指定证书的主题为 '/CN=ip',ip修改为自己的访问地址IP
openssl req -new -out dashboard.csr -key dashboard.key -subj '/CN=<ip>'
#使用 OpenSSL 对证书签名请求文件 dashboard.csr 进行签名，使用 dashboard.key 作为私钥，并将签名后的证书保存为 dashboard.crt，设置有效期为 365 天
openssl x509 -req -days 365 -in dashboard.csr -signkey dashboard.key -out dashboard.crt
#显示kubernetes-dashboard命名空间
kubectl get secret kubernetes-dashboard-certs -n kubernetes-dashboard
#用于删除位于 kubernetes-dashboard 命名空间中名为 kubernetes-dashboard-certs 的 Secret 对象
kubectl delete secret kubernetes-dashboard-certs -n kubernetes-dashboard
#用于在 kubernetes-dashboard 命名空间中创建一个名为 kubernetes-dashboard-certs 的 Secret 对象，并将 dashboard.key 和 dashboard.crt 两个文件作为其内容
kubectl create secret generic kubernetes-dashboard-certs --from-file=dashboard.key --from-file=dashboard.crt -n kubernetes-dashboard
#查看dashboard的pod
kubectl get pod -n kubernetes-dashboard  | grep dashboard
#删除原有pod即可（会自动创建新的pod），pod名称需要替换自己查询到的名称。
kubectl delete pod kubernetes-dashboard-6bd77794f-w8xgc -n kubernetes-dashboard