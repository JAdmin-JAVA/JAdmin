
// 新建cookie。
// hours为空字符串时,cookie的生存期至浏览器会话结束。hours为数字0时,建立的是一个失效的cookie,这个cookie会覆盖已经建立过的同名、同path的cookie（如果这个cookie存在）。
function setCookie(name, value, hours, path) {
	var name = escape(name);
	var value = escape(value);
	var expires = new Date();
	expires.setTime(expires.getTime() + hours * 3600000);
	path = path == "" ? "" : ";path=" + path;
	_expires = (typeof hours) == "string" ? "" : ";expires="
			+ expires.toUTCString();
	document.cookie = name + "=" + value + _expires + path;
}

// 获取cookie值
function getCookieValue(name) {
	var name = escape(name);
	// 读cookie属性，这将返回文档的所有cookie
	var allcookies = document.cookie;
	// 查找名为name的cookie的开始位置
	name += "=";
	var pos = allcookies.indexOf(name);
	// 如果找到了具有该名字的cookie，那么提取并使用它的值
	if (pos != -1) { // 如果pos值为-1则说明搜索"version="失败
		var start = pos + name.length; // cookie值开始的位置
		var end = allcookies.indexOf(";", start); // 从cookie值开始的位置起搜索第一个";"的位置,即cookie值结尾的位置
		if (end == -1)
			end = allcookies.length; // 如果end值为-1说明cookie列表里只有一个cookie
		var value = allcookies.substring(start, end); // 提取cookie的值
		return unescape(value); // 对它解码
	} else
		return ""; // 搜索失败，返回空字符串
}

// 删除cookie
function deleteCookie(name, path) {
	var name = escape(name);
	var expires = new Date(0);
	path = path == "" ? "" : ";path=" + path;
	document.cookie = name + "=" + ";expires=" + expires.toUTCString() + path;
}