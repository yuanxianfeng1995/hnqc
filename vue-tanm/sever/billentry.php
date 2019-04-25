<?php
$con = mysqli_connect('localhost','root','root');
if (!$con)
{
    Response::json(0,'失败');
}
// 选择数据库
mysqli_select_db($con,"user");
// 设置编码，防止中文乱码
mysqli_set_charset($con, "utf8");

$sql="SELECT * FROM billentry";

$result = mysqli_query($con,$sql);

function getByPid($arr){
    $result = array();
    foreach($arr as $v){
        $result[] = $v;
    }
    return $result;
}
//查询数据
$result = getByPid($result);
//输出JSON数据
echo json_encode($result);

mysqli_close($con);
?>

