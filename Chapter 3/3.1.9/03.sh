#用于配置iptables规则的命令。它设置了默认的转发策略为接受（ACCEPT），意味着当数据包经过网络设备时，如果没有匹配到任何其他规则，就会被允许继续转发。
iptables -P FORWARD ACCEPT
#一个用于关闭所有活动的交换空间（swap）的命令。交换空间是在硬盘上创建的一块用于存储内存中暂时不使用的数据的区域。当系统的物理内存不足时，操作系统可以将一部分内存中的数据转移到交换空间中，以释放物理内存供其他进程使用。
swapoff -a
#命令的作用是将/etc/fstab文件中包含关键字"swap"的行注释掉，注释的方式是在行的开头添加"#"字符。这样做可以阻止系统在启动时自动挂载交换空间。
sed -i '/ swap / s/^\(.*\)$/#\1/g' /etc/fstab
#一个用于临时禁用 SELinux（Security-Enhanced Linux）的命令。
sudo setenforce 0
#将SELINUX的值从enforcing改为permissive。这意味着在下次系统启动时，SELinux将以"Permissive"模式运行，仍然记录违规行为但不会阻止它们的执行。请注意，修改配置文件后，需要重新启动系统才能使更改生效。
sudo sed -i 's/^SELINUX=enforcing$/SELINUX=permissive/' /etc/selinux/config
#用于加载名为 "br_netfilter" 的内核模块的命令。
sudo modprobe br_netfilter
#通过执行上述命令并将配置写入相应的文件，可以确保在系统启动时加载必需的内核br_netfilter模块并设置正确的系统参数。这对于 Kubernetes 集群的正常运行是必要的。
cat <<EOF | sudo tee /etc/modules-load.d/k8s.conf
br_netfilter
EOF
 
cat <<EOF | sudo tee /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
net.ipv4.ip_forward=1
vm.max_map_count=262144
EOF
#重新加载系统的 sysctl 配置文件，以使更改的系统参数生效。
sudo sysctl --system