#创建一个名为 .kube 的目录，使用 $HOME 变量表示用户的主目录。
mkdir -p $HOME/.kube
#复制管理员配置文件 /etc/kubernetes/admin.conf 到用户的 .kube/config 文件中。-i 参数表示如果目标文件已存在，则提示用户是否覆盖。
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
#使用 chown 命令将 .kube/config 文件的所有权更改为当前用户。$(id -u) 表示当前用户的用户 ID，$(id -g) 表示当前用户的组 ID。
sudo chown $(id -u):$(id -g) $HOME/.kube/config