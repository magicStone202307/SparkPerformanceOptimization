#定义了 Kubernetes 的 YUM 软件源，使得可以通过 YUM 包管理器从该软件源安装和更新 Kubernetes 相关的软件包。其中 baseurl 指定了软件源的基本 URL，gpgkey 指定了用于验证软件包的 GPG 密钥。
cat <<EOF > /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=1
repo_gpgcheck=1
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF
#用于管理和更新 yum 软件包管理器的缓存。
yum clean all && yum makecache