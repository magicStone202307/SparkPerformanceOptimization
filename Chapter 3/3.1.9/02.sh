# 创建一个目录以存放二进制文件
mkdir kubernetes-binaries
cd kubernetes-binaries
# 下载 kubelet、kubeadm 和 kubectl 二进制文件
wget https://storage.googleapis.com/kubernetes-release/release/v1.27.1/bin/linux/amd64/kubelet
wget https://storage.googleapis.com/kubernetes-release/release/v1.27.1/bin/linux/amd64/kubeadm
wget https://storage.googleapis.com/kubernetes-release/release/v1.27.1/bin/linux/amd64/kubectl
# 赋予二进制文件可执行权限
chmod +x kubelet kubeadm kubectl
#将下载的二进制文件移动到 /usr/local/bin/ 目录
sudo mv kubelet kubeadm kubectl /usr/local/bin/
#安装 kubelet 服务单元文件，以便将其作为系统服务运行
# 创建 kubelet 服务单元文件
cat << EOF | sudo tee /etc/systemd/system/kubelet.service
[Unit]
Description=Kubernetes Kubelet
Documentation=https://kubernetes.io/docs/concepts/overview/components/#kubelet

[Service]
ExecStart=/usr/local/bin/kubelet
Restart=always
StartLimitInterval=0
RestartSec=10

[Install]
WantedBy=multi-user.target
EOF
#启动和启用 kubelet 服务
sudo systemctl daemon-reload
sudo systemctl enable kubelet
sudo systemctl start kubelet
#确认 kubelet 服务已成功启动
sudo systemctl status kubelet