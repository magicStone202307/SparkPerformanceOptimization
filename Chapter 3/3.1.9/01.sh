#下载并安装 kubectl
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
#为 kubectl 添加可执行权限
chmod +x kubectl
#将 kubectl 可执行文件移动到系统的可执行路径下
sudo mv kubectl /usr/local/bin/
#验证 kubectl 是否已成功安装
kubectl version --client