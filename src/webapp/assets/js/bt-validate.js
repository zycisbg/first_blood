!function($) {
    var obj;
    $.fn.validation = function(options) {
        return this.each(function() {
            globalOptions = $.extend({}, $.fn.validation.defaults, options);
            obj=this;
            reg(obj);
            validationForm(obj);
        });
    };
    
    $.fn.validation.defaults = {
        validRules : [
            {name: 'required', validate: function(el) {return ($.trim(el.val()) == '');}, defaultMsg: '必须填写.'},
            {name: 'number', validate: function(el) {return (!/^[0-9]\d*$/.test(el.val()));}, defaultMsg: '请输入数字.'},
            {name: 'email', validate: function(el) {return (!/^[a-zA-Z0-9]{1}([\._a-zA-Z0-9-]+)(\.[_a-zA-Z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+){1,3}$/.test(el.val()));}, defaultMsg: '邮箱格式不正确.'},
            {name: 'char', validate: function(el) {return (!/^[a-z\_\-A-Z]*$/.test(el.val()));}, defaultMsg: '请输入英文字符.'},
            {name: 'chinese', validate: function(el) {return (!/^[\u4e00-\u9fff]$/.test(el.val()));}, defaultMsg: '请输入中文字符'},
            {name: 'equalTo', validate: function(el) {return (el.val() != $(el.attr('btvd-equalTo')).val());}, defaultMsg: '两次输入不一致'},
            {name: 'minlength', validate: function(el) {return ($.trim(el.val()).length < el.attr('btvd-minlength'));}, defaultMsg: '输入的值不能小于最小长度'},
            {name: 'decimals', validate: function(el) {return (!/^[+]?[\d]+(([\.]{1}[\d]+)|([\d]*))$/.test(el.val()));}, defaultMsg: '请输入小数或整数.'}
        ]
    };
    
    var formState = false, fieldState = false, wFocus = false, globalOptions = {};

    var validateField = function(field, valid) {
        var el = $(field), error = false, errorMsg = '',
        crule=(el.attr('btvd-el')==undefined)?null:el.attr('btvd-el').split(' '),
        msg = (el.attr('btvd-err')==undefined)?null:el.attr('btvd-err').split(' ');
        if(crule){
            if( ! eval(crule[0]).test(el.val()) ) {
                error = true;
                errorMsg =msg;
            }
        } else {
            for (i = 0; (i < valid.length) ; i++) {
                var x = true, flag = valid[i];
                if (flag.substr(0, 1) == '!') {
                    x = false;
                    flag = flag.substr(1, flag.length - 1);
                }

                var rules = globalOptions.validRules;
                for (j = 0; j < rules.length; j++) {
                    var rule = rules[j];
                    if (flag == rule.name) {
                        if (rule.validate.call(field, el) == x) {
                            error = true;
                            errorMsg = (msg == null)?rule.defaultMsg:msg;
                            break;
                        }
                    }
                }

                if (error) {break;}
            }
        }
        
        if (error) {
        	if (el.attr('data-rel') == undefined) {
        		el.attr('data-rel', 'popover').attr('data-placement', 'right').attr('data-content', errorMsg).attr('data-trigger', 'manual');
        	}
        	el.popover('show');
        } else {
        	el.popover('hide');
        }
        
        return !error;
    };

    var reg = function(obj){
        $('input, textarea').each(function() {
            var el = $(this), valid = (el.attr('btvd-type')==undefined)?null:el.attr('btvd-type').split(' ');
            valid1 = (el.attr('btvd-el')==undefined)?null:el.attr('btvd-el').split(' ');
            if (valid != null && valid.length > 0   || valid1 != null && valid1.length > 0 ) {                       
                el.blur(function() {
                    validateField(this, valid);
                });
            }
        });
    }

    var validationForm = function(obj) {
        $(obj).submit(function() {
        	$(obj).find('button[type=submit]').button('loading');
            if (formState) {
                return false;
            }
            formState = true;
            var validationError = false;
            $('input, textarea', this).each(function () {
                var el = $(this), valid = (el.attr('btvd-type')==undefined)?null:el.attr('btvd-type').split(' ');
                if (valid != null && valid.length > 0) {
                    if (!validateField(this, valid)) {
                        if (wFocus == false) {
                            scrollTo(0, el[0].offsetTop - 50);
                            wFocus = true;
                        }

                        validationError = true;
                    }
                }
            });

            wFocus = false;
            fieldState = true;

            if (validationError) {
                formState = false;
                $(obj).find('button[type=submit]').button('reset');
                return false;
            }

            return true;
        });
    };
}(window.jQuery);

$(function() {
	$('.bt-validate').each(function() {
		$(this).validation();
	});
});