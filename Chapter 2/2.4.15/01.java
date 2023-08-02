import alluxio.AlluxioURI;
import alluxio.client.file.FileSystem;
import alluxio.client.file.URIStatus;
import alluxio.conf.InstancedConfiguration;
import alluxio.conf.PropertyKey;
import alluxio.exception.AlluxioException;

import java.io.IOException;
import java.util.List;

public class AlluxioExample {
    public static void main(String[] args) throws IOException, AlluxioException {
        // 创建 Alluxio 文件系统实例
        InstancedConfiguration conf = new InstancedConfiguration.Builder().build();
        FileSystem alluxioFs = FileSystem.Factory.create(conf);

        // 指定要操作的文件路径
        String filePath = "/path/to/file";

        // 创建 Alluxio 文件路径
        AlluxioURI uri = new AlluxioURI(filePath);

        // 检查文件是否存在
        boolean exists = alluxioFs.exists(uri);
        System.out.println("File exists: " + exists);

        // 创建文件夹
        AlluxioURI dirPath = new AlluxioURI("/path/to/directory");
        alluxioFs.createDirectory(dirPath);

        // 上传本地文件到 Alluxio
        String localFilePath = "/path/to/local/file";
        alluxioFs.copyFromLocalFile(new AlluxioURI(localFilePath), uri);

        // 从 Alluxio 下载文件到本地
        String localDestPath = "/path/to/local/destination";
        alluxioFs.copyToLocalFile(uri, new AlluxioURI(localDestPath));

        // 获取文件状态
        URIStatus status = alluxioFs.getStatus(uri);
        System.out.println("File size: " + status.getLength());

        // 列出目录下的文件和子目录
        List<URIStatus> children = alluxioFs.listStatus(dirPath);
        for (URIStatus child : children) {
            System.out.println(child.getPath());
        }

        // 删除文件
        alluxioFs.delete(uri);

        // 关闭 Alluxio 文件系统实例
        alluxioFs.close();
    }
}