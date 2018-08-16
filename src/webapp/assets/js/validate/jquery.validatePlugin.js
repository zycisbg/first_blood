$(document).ready(function(){
	jQuery.validator.addMethod("mobilecheck", function(value, element) {
     var length = value.length;   
     var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;   
     return (length == 11 && mobile.exec(value))? true:false;
	 }, "请正确填写您的手机号码");
	jQuery.validator.addMethod("loginNamecheck", function(value, element) {
	    var chrnum = /^[a-zA-Z][a-zA-Z_0-9]{0,}$/;
	    return this.optional(element) || (chrnum.test(value));
	}, "只能输入数字和字母,并且已字母开头");
	// 不能有空格
	jQuery.validator.addMethod("trimCheck", function(value, element) {
	var result = value.indexOf(" ")>-1;
	return this.optional(element) || (!result);
	}, $.validator.format("不能包含空格！"));
	
	jQuery.validator.addMethod("lrunlv", function(value, element) {       
			     return this.optional(element) || /^\d+(\.\d{1,2})?$/.test(value);       
			 }, "小数位不能超过三位");
	
	// 不能有中文
	jQuery.validator.addMethod("chineseCheck", function(value, element) {
	var patton = /^[\u4e00-\u9fa5]+$/;
	var result = patton.test(value);
	return this.optional(element) || (!result);
	}, $.validator.format("不能包含中文！"));
	
	// 字符最小长度验证（一个中文字符长度为2）
	jQuery.validator.addMethod("stringMinLength", function(value, element, param) {
	var length = value.length;
	for ( var i = 0; i < value.length; i++) {
	if (value.charCodeAt(i) > 127) {
	length++;
	}
	}
	return this.optional(element) || (length >= param);
	}, $.validator.format("长度不能小于{0}!"));
	
	// 字符最大长度验证（一个中文字符长度为2）
	jQuery.validator.addMethod("stringMaxLength", function(value, element, param) {
	var length = value.length;
	for ( var i = 0; i < value.length; i++) {
	if (value.charCodeAt(i) > 127) {
	length++;
	}
	}
	return this.optional(element) || (length <= param);
	}, $.validator.format("长度不能大于{0}!"));
	
	 // 字符验证      
	jQuery.validator.addMethod("stringCheck", function(value, element) {      
	return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);      
	}, "只能包括中文字、英文字母、数字和下划线");  
	
	// 中文字两个字节      
	jQuery.validator.addMethod("byteRangeLength", function(value, element, param) {      
	var length = value.length;      
	for(var i = 0; i < value.length; i++){      
	if(value.charCodeAt(i) > 127){      
	length++;      
	}      
	}      
	return this.optional(element) || ( length >= param[0] && length <= param[1] );      
	}, "请确保输入的值在3-15个字节之间(一个中文字算2个字节)");  
	
	// 字符验证
	jQuery.validator.addMethod("string", function(value, element) {
	return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);
	}, "不允许包含特殊符号!");
	// 必须以特定字符串开头验证
	jQuery.validator.addMethod("begin", function(value, element, param) {
	var begin = new RegExp("^" + param);
	return this.optional(element) || (begin.test(value));
	}, $.validator.format("必须以 {0} 开头!"));
	// 验证两次输入值是否不相同
	jQuery.validator.addMethod("notEqualTo", function(value, element, param) {
	return value != $(param).val();
	}, $.validator.format("两次输入不能相同!"));
	// 验证值不允许与特定值等于
	jQuery.validator.addMethod("notEqual", function(value, element, param) {
	return value != param;
	}, $.validator.format("输入值不允许为{0}!"));
	
	// 验证值必须大于特定值(不能等于)
	jQuery.validator.addMethod("gt", function(value, element, param) {
	return value > param;
	}, $.validator.format("输入值必须大于{0}!"));
	
	// 验证值小数位数不能超过两位
	jQuery.validator.addMethod("decimal", function(value, element) {
	var decimal = /^-?\d+(\.\d{1,2})?$/;
	return this.optional(element) || (decimal.test(value));
	}, $.validator.format("小数位数不能超过两位!"));
	//字母数字
	jQuery.validator.addMethod("alnum", function(value, element) {
		return this.optional(element) || /^[\uFFE5\w]+$/.test(value);    
	}, "只能包括英文字母、数字和下划线");
	// 汉字
	jQuery.validator.addMethod("chcharacter", function(value, element) {
	var tel = /^[\u4e00-\u9fa5]+$/;
	return this.optional(element) || (tel.test(value));
	}, "请输入汉字");
	// 身份证号码验证      
	jQuery.validator.addMethod("isIdCardNo", function(value, element) {
	return this.optional(element) || /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/.test(value)||/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z])$/.test(value);      
	}, "请正确输入您的身份证号码");
	
	// 手机号码验证      
	jQuery.validator.addMethod("isMobile", function(value, element) {      
	var length = value.length;  
	var mobile = /^(((13[0-9]{1})|(18[0-9]{1})|(17[0-9]{1})|(15[0-9]{1}))+\d{8})$/;  
	return this.optional(element) || (length == 11 && mobile.test(value));      
	}, "请正确填写您的手机号码");      
	
	// 电话号码验证      
	jQuery.validator.addMethod("isTel", function(value, element) {      
	var tel = /^\d{3,4}-?\d{7,9}$/;    //电话号码格式010-12345678  
	return this.optional(element) || (tel.test(value));      
	}, "请正确填写您的电话号码");  
	
	// 联系电话(手机/电话皆可)验证  
	jQuery.validator.addMethod("isPhone", function(value,element) {  
	var length = value.length;  
	var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;  
	var tel = /^\d{3,4}-?\d{7,9}$/;  
	return this.optional(element) || (tel.test(value) || mobile.test(value));  
	
	}, "请正确填写您的联系电话");  
	
	// 邮政编码验证      
	jQuery.validator.addMethod("isZipCode", function(value, element) {      
	var tel = /^[0-9]{6}$/;      
	return this.optional(element) || (tel.test(value));      
	}, "请正确填写您的邮政编码");   
	
	//标签验证；以","分隔
	jQuery.validator.addMethod("isTags", function(value, element) { 
		var flag = true;
		value = value.replace(/;/ig," ").replace(/；/ig," ").replace(/，/ig," ").replace(/,/ig," ");
		if(value.indexOf(" ")==-1 && value.length > 7){
			flag = false;
		}
		if(value.indexOf(" ")!=-1){
			var t = value.split(" ");
			for(var i=0;i<t.length;i++){
				if(t[i].length > 7){
					flag = false;
				}
			}
		}
		return this.optional(element) || (flag);      
		}, "每个标签字符长度不能大于7个字符，请重新输入！");   
	// 不能输入过去的日期
	jQuery.validator.addMethod("isOldDate", function(value, element) {     
		var flag = true;
		var date = new Date();
   		var startDate = value.split("-");
   		var starttime = new Date(startDate[0]+"/"+startDate[1]+"/"+startDate[2]+" 23:59:59");
   		if(starttime.getTime()<date.getTime()){
   			flag = false;
   		}   
	return this.optional(element) || (flag);      
	}, "不能输入过去的日期"); 
});