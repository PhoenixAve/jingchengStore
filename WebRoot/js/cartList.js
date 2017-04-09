$(function() {
	showTotal(); //显示合计
	// 给全选按钮添加点击事件
	$("#selectAll").click(function() {
		var flag = $(this).prop("checked"); //获取全选的状态
		setItemCheckBox(flag); //让所有条目复选框与全选同步
		setJieSuanStyle(flag); //让结算按钮与全选同步
		showTotal(); //重新计算合计
	});

	// 给条目复选框添加事件
	$("[name=checkboxBtn]:checkbox").click(function() {
		var selectedCount = $("input[type=checkbox][name=checkboxBtn]:checked").length; //被勾选复选框个数
		var allCount = $("input[type=checkbox][name=checkboxBtn]").length; //所有条目复选框个数
		if (selectedCount == allCount) { //全选了
			$("#selectAll").prop("checked", true); //勾选全选复选框
			setJieSuanStyle(true); //使结算按钮可用
		} else if (selectedCount == 0) { //全撤消了
			$("#selectAll").prop("checked", false); //撤消全选复选框
			setJieSuanStyle(false); //使结算按钮不可用			
		} else { //未全选
			$("#selectAll").prop("checked", false); //撤消全选复选框
			setJieSuanStyle(true); //使结算按钮可用
		}
		showTotal(); //重新计算合计
	});

	// 给jian添加事件
	$(".jian").click(function() {
		// 获取cartItemId
		var id = $(this).attr("id").substring(0, 32);
		// 获取输入框中的数量
		var quantity = $("#" + id + "Quantity").val();
		// 判断当前数量是否为1，如果为1,那就不是修改数量了，而是要删除了。
		if(quantity == 1) {
			if(confirm("您是否真要删除该条目？")) {
				location = "/jingchengStore/CartItemServlet?method=batchDelete&cartItemIds=" + id;
			}
		} else {
			sendUpdateQuantity(id, quantity-1);
		}
	});
	// 给加号添加click事件
	$(".jia").click(function() {
		// 获取cartItemId
		var id = $(this).attr("id").substring(0, 32);
		// 获取输入框中的数量
		var quantity = $("#" + id + "Quantity").val();
		sendUpdateQuantity(id, Number(quantity)+1);
	});
});

//请求服务器，修改数量。
function sendUpdateQuantity(id, quantity) {
	$.ajax({
		url:"/jingchengStore/CartItemServlet",//要请求的servlet
		data:{method:"updateQuantity",cartItemId:id,quantity:quantity},//给服务器的参数
		type:"POST",
		dataType:"json",
		async:false,//是否异步请求，如果是异步，那么不会等服务器返回，我们这个函数就向下运行了。
		cache:false,
		success:function(result) {
			//1. 修改数量
			$("#" + id + "Quantity").val(result.quantity);
			//2. 修改小计
			$("#" + id + "Subtotal").text(result.subtotal);
			//3. 重新计算总计
			showTotal();
		},
	});
}

// 设置所有条目复选框
function setItemCheckBox(flag) {
	$("input[type=checkbox][name=checkboxBtn]").prop("checked", flag); //让所有条目的复选框与参数flag同步
}

// 设置结算按钮的样式
function setJieSuanStyle(flag) {
	if (flag) { // 有效状态
		$("#jiesuan").removeClass("kill").addClass("jiesuan"); //切换样式
		$("#jiesuan").unbind("click"); //撤消“点击无效”
	} else { // 无效状态
		$("#jiesuan").removeClass("jiesuan").addClass("kill"); //切换样式
		$("#jiesuan").click(function() { //使其“点击无效”
			return false;
		});
	}
}

// 显示合计
function showTotal() {
	var total = 0; //创建total，准备累加
	/*
	1. 获取所有被勾选的复选框，遍历之
	*/
	$("input[type=checkbox][name=checkboxBtn]:checked").each(function() {
		/*
		2. 通过复选框找到小计
		*/
		var subtotal = Number($("#" + $(this).val() + "Subtotal").text());
		total += subtotal;
	});
	/*
	3. 设置合计
	*/
	$("#total").text(round(total, 2));
}
/*
 * 批量删除
 */
function batchDelete() {
	// 1. 获取所有被选中条目的复选框
	// 2. 创建一数组，把所有被选中的复选框的值添加到数组中
	// 3. 指定location为CartItemServlet，参数method=batchDelete，参数cartItemIds=数组的toString()
	var cartItemIdArray = new Array();
	$("input[type=checkbox][name=checkboxBtn]:checked").each(function() {
		cartItemIdArray.push($(this).val());//把复选框的值添加到数组中
	});
	location = "/jingchengStore/CartItemServlet?method=batchDelete&cartItemIds=" + cartItemIdArray;
}
/*
 * 结算
 */
function jiesuan() {
	// 1. 获取所有被选择的条目的id，放到数组中
	var cartItemIdArray = new Array();
	$("input[type=checkbox][name=checkboxBtn]:checked").each(function() {
		cartItemIdArray.push($(this).val());//把复选框的值添加到数组中
	});	
	// 2. 把数组的值toString()，然后赋给表单的cartItemIds这个hidden
	$("#cartItemIds").val(cartItemIdArray.toString());
	// 把总计的值，也保存到表单中
	$("#hiddenTotal").val($("#total").text());
	// 3. 提交这个表单
	$("#jieSuanForm").submit();
}