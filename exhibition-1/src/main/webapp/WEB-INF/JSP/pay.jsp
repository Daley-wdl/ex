<%--
  Created by IntelliJ IDEA.
  User: zks
  Date: 2017/9/21
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>在线支付</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/order/payCart.action" method="post">
        <table width="60%">
            <tr>
                <td bgcolor="#F7FEFF" colspan="4">
                    订单号：${param.id }<input type="hidden" value="${param.id }" name="id"/>
                    支付金额：${param.money }元
                </td>
            </tr>
            <tr><td><br/></td></tr>
            <tr>
                <td>请您选择在线支付银行</td>
            </tr>
            <tr>
                <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CMBCHINA-NET-B2C">招商银行 </td>
                <td><INPUT TYPE="radio" NAME="pd_FrpId" value="ICBC-NET">工商银行</td>
                <td><INPUT TYPE="radio" NAME="pd_FrpId" value="ABC-NET">农业银行</td>
                <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CCB-NET">建设银行 </td>
            </tr>
            <tr>
                <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CMBC-NET">中国民生银行总行</td>
                <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CEB-NET" >光大银行 </td>
                <td><INPUT TYPE="radio" NAME="pd_FrpId" value="BOCO-NET">交通银行</td>
                <td><INPUT TYPE="radio" NAME="pd_FrpId" value="SDB-NET">深圳发展银行</td>
            </tr>
            <tr>
                <td><INPUT TYPE="radio" NAME="pd_FrpId" value="BCCB-NET">北京银行</td>
                <td><INPUT TYPE="radio" NAME="pd_FrpId" value="CIB-NET">兴业银行 </td>
                <td><INPUT TYPE="radio" NAME="pd_FrpId" value="SPDB-NET">上海浦东发展银行 </td>
                <td><INPUT TYPE="radio" NAME="pd_FrpId" value="ECITIC-NET">中信银行</td>
            </tr>
            <tr><td><br/></td></tr>
            <tr>
                <td><INPUT TYPE="submit" value="确定支付"></td>
            </tr>
        </table>
    </form>
</body>
</html>
