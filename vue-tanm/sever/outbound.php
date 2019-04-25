<?php
$con = mysqli_connect('localhost','root','root');
if(!$con){
	die("can't connect".mysqli_error());//如果链接失败输出错误
}
// 选择数据库
mysqli_select_db($con,"user");
// 设置编码，防止中文乱码
mysqli_set_charset($con, "utf8");

$type = $_SERVER[ 'REQUEST_METHOD' ];
function getByPid($arr){
    $result = array();
    foreach($arr as $v){
        $result[] = $v;
    }
    return $result;
}
if ($type === 'GET'){
	$sql="SELECT * FROM outbound";
	$result = $con->query($sql);
    return json_encode($result);
//	if (mysqli_num_rows($result) > 0) {
		//查询数据
//		$result2 = getByPid($result);
//		$arr = array(
//			'code' => '0',
//			'data' => array(
//				'items' => $result2,
//				'orderBy' => "id",
//				'pageNo' => "0",
//				'pageSize' => count($result2)/13,
//				'totalCount' => count($result2)
//			),
//			'message' => '处理成功',
//			'success' => 'turn'
//		);
//		echo json_encode($result);

//	}
}
//if ($type === 'POST') {
////首先进行非空排错
//    header('Access-Control-Allow-Origin:*');
//    $final = file_get_contents('php://input');
//    $data = urldecode($final);
//	if (empty($data)) {
//		die('没有数据');
//	}
//    $xs = json_decode($data);
//    // $sql2 = "INSERT INTO outbound ('name',manufacturer,price) VALUES ($xs->name,$xs->manufacturer,$price)";
//    $sql2 ="insert into outbound (name, manufacturer, price,remark) VALUES ('$xs->name', '$xs->manufacturer', $xs->price,'$xs->remark')";
//	$retval = mysqli_query($con, $sql2);
//	$rows   = mysqli_affected_rows($con);
//        // 保证操作成功了，就跳转
//        if($rows > 0 )
//        {
//            $result = getByPid($xs);
//            $arr = array(
//                'code' => '0',
//                'data' => $result,
//                'message' => '处理成功',
//                'success' => 'turn'
//            );
//            echo json_encode($arr);
//        } else {
//            $arr = array(
//                'code' => '1',
//                'data' => $sql2,
//                'message' => '处理失败',
//                'success' => 'false'
//            );
//            echo json_encode($arr);
//        }
//}
  mysqli_close($con);

