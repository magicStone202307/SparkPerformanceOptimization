kubeadm config print init-defaults > defaults.yaml
#用于将旧版本的配置文件升级到与新版本兼容的格式，以便在升级 Kubernetes 版本时保留现有的配置信息。在迁移完成后，您可以使用新的配置文件来进行初始化、升级或维护 Kubernetes 集群。
kubeadm config migrate --old-config defaults.yaml --new-config kubeadm.yaml
#执行该命令后，kubeadm.yaml 文件中的 advertiseAddress 字段的值将被替换为指定的 <ip> 值。请将 <ip> 替换为要使用的实际 IP 地址。
sed -i 's/advertiseAddress:.*$/advertiseAddress: <ip>/g' kubeadm.yaml
#执行该命令后，kubeadm.yaml 文件中的 name 字段的值将被替换为指定的 <hostname> 值。请将 <hostname> 替换为要使用的实际主机名。
sed -i 's/name: node/name: bdp-clickhouse-test-1/g' kubeadm.yaml
#修改镜像源
sed -i 's#imageRepository:.*$#imageRepository: registry.aliyuncs.com/google_containers#g' kubeadm.yaml
#配置Pod 网段
sed -i "/networking:/a\  podSubnet: 10.244.0.0/16" kubeadm.yaml