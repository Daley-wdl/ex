<!doctype html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>注册页面</title>
    <!-- Include the FontAwesome CSS if you want to use feedback icons provided by FontAwesome -->
    <!--<link rel="stylesheet" href="http://www.jq22.com/jquery/font-awesome.4.6.0.css" />-->
    <link rel="stylesheet" href="vendor/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" href="dist/css/formValidation.css"/>
    <link rel="stylesheet" type="text/css" href="css/default.css">

</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2">
            <div class="page-header">
                <h2>注册</h2>
            </div>

            <div class="panel-group" id="steps">
                <!-- Step 1 -->

                <form id="defaultForm" method="post" class="form-horizontal" action="">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4 class="panel-title"><a data-toggle="collapse" data-parent="#steps" href="#stepOne">用户注册</a></h4>
                        </div>
                        <div id="stepOne" class="panel-collapse collapse in">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-lg-3 control-label">用户名</label>
                                    <div class="col-lg-5">
                                        <input type="text" class="form-control" name="username" placeholder="用户名"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-lg-3 control-label">身份证号</label>
                                    <div class="col-lg-5">
                                        <input type="text" class="form-control" name="IDCard" placeholder="身份证号" />
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-lg-3 control-label">手机号</label>
                                    <div class="col-lg-5">
                                        <input type="text" class="form-control" name="phoneNumber" placeholder="手机号" />
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-lg-3 control-label">邮箱</label>
                                    <div class="col-lg-5">
                                        <input type="text" class="form-control" name="email" placeholder="邮箱"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-lg-3 control-label">密码</label>
                                    <div class="col-lg-5">
                                        <input type="password" class="form-control" name="password" placeholder="密码"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-lg-3 control-label">确认密码</label>
                                    <div class="col-lg-5">
                                        <input type="password" class="form-control" name="passwordagain" placeholder="确认密码"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-lg-4 col-lg-offset-3">
                                        <button type="submit" class="btn btn-success " name="signup" value="Sign up">&nbsp;&nbsp;注册&nbsp;&nbsp;</button>
                                        <button type="reset" class="btn btn-warning" name="reset" value="重置">&nbsp;&nbsp;重置&nbsp;&nbsp;</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                <!-- Step 3 -->
                <form id="defaultForm2" method="post" class="form-horizontal" action="">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4 class="panel-title"><a data-toggle="collapse" data-parent="#steps" href="#stepThree">展商注册</a></h4>
                        </div>
                        <div id="stepThree" class="panel-collapse collapse">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-lg-3 control-label">用户名</label>
                                    <div class="col-lg-5">
                                        <input type="text" class="form-control" name="username" placeholder="用户名"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-lg-3 control-label">身份证号</label>
                                    <div class="col-lg-5">
                                        <input type="text" class="form-control" name="IDCard" placeholder="身份证号" />
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-lg-3 control-label">手机号</label>
                                    <div class="col-lg-5">
                                        <input type="text" class="form-control" name="phoneNumber" placeholder="手机号" />
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-lg-3 control-label">邮箱</label>
                                    <div class="col-lg-5">
                                        <input type="text" class="form-control" name="email" placeholder="邮箱"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-lg-3 control-label">密码</label>
                                    <div class="col-lg-5">
                                        <input type="password" class="form-control" name="password" placeholder="密码"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-lg-3 control-label">确认密码</label>
                                    <div class="col-lg-5">
                                        <input type="password" class="form-control" name="passwordagain" placeholder="确认密码"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-lg-3 control-label">简介</label>
                                    <div class="col-lg-5">
                                        <textarea class="form-control" name="bio" rows="6" data-fv-stringlength data-fv-stringlength-max="150" data-fv-stringlength-message="简介内容不可以超过150字"></textarea>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-lg-3 control-label">上传照片</label>
                                    <div class="col-lg-7">
                                        <input type="file" class="form-control" name="secondFile" />
                                        <span class="help-block">请上传身份证正面照片</span>
                                    </div>

                                    <label class="col-lg-3 control-label"></label>
                                    <div class="col-lg-7">
                                        <input type="file" class="form-control" name="secondFile" />
                                        <span class="help-block">请上传身份证反面照片</span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-lg-4 col-lg-offset-3">
                                        <button type="submit" class="btn btn-success " name="signup" value="Sign up">&nbsp;&nbsp;注册&nbsp;&nbsp;</button>
                                        <button type="reset" class="btn btn-warning" name="reset" value="重置">&nbsp;&nbsp;重置&nbsp;&nbsp;</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>

<script type="text/javascript" src="vendor/jquery/jquery.min.js"></script>
<script type="text/javascript" src="vendor/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="dist/js/formValidation.js"></script>
<script type="text/javascript" src="dist/js/framework/bootstrap.js"></script>
<script type="text/javascript" src="dist/js/language/zh_CN.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $('#defaultForm').formValidation({
            message: '信息填写错误，请重新输入',
            excluded: ':disabled',
            icon: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                username: {
                    message: '信息格式填写错误',
                    validators: {
                        notEmpty: {
                            message: '信息不能为空'
                        },
                        stringLength: {
                            min: 3,
                            max: 12,
                            message: '用户名长度必须在3~12位之间'
                        },
                        regexp: {
                            regexp: /^[u0391-uFFE5w]+$/,
                            message: '用户名只能包括中文字、英文字母、数字和下划线'
                        }
                    }
                },
                IDCard: {
                    validators: {
                        notEmpty: {
                            message: '信息不能为空'
                        },
                        regexp: {
                            regexp: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
                            message: '身份证号输入错误'
                        }
                    }
                },
                email: {
                    validators: {
                        notEmpty: {
                            message: '信息不能为空'
                        },
                        regexp: {
                            regexp: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/,
                            message: '邮箱输入错误'
                        }
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        different: {
                            field: 'username',
                            message: '密码不能和用户名一样'
                        }
                    }
                },
                passwordagain: {
                    validators: {
                        notEmpty: {
                            message: '内容不可为空'
                        },
                        identical: {
                            field: 'password',
                            message: '两次填写密码不一致'
                        }
                    }
                },
                phoneNumber: {
                    validators: {
                        notEmpty: {
                            message: '信息不能为空'
                        },

                        regexp: {
                            regexp: /^1(3|4|5|7|8)\d{9}$/,
                            message: '手机号码格式错误'
                        }
                    }
                }
            }
        }).on('err.form.fv', function(e) {
            console.log('error');

            // Active the panel element containing the first invalid element
            var $form         = $(e.target),
                validator     = $form.data('formValidation'),
                $invalidField = validator.getInvalidFields().eq(0),
                $collapse     = $invalidField.parents('.collapse');

            $collapse.collapse('show');
        });




        $('#defaultForm2').formValidation({
            message: '信息填写错误，请重新输入',
            excluded: ':disabled',
            icon: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                username: {
                    message: '信息格式填写错误',
                    validators: {
                        notEmpty: {
                            message: '信息不能为空'
                        },
                        stringLength: {
                            min: 3,
                            max: 12,
                            message: '用户名长度必须在3~12位之间'
                        },
                        regexp: {
                            regexp: /^[u0391-uFFE5w]+$/,
                            message: '用户名只能包括中文字、英文字母、数字和下划线'
                        }
                    }
                },
                IDCard: {
                    validators: {
                        notEmpty: {
                            message: '信息不能为空'
                        },
                        regexp: {
                            regexp: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
                            message: '身份证号输入错误'
                        }
                    }
                },
                email: {
                    validators: {
                        notEmpty: {
                            message: '信息不能为空'
                        },
                        regexp: {
                            regexp: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/,
                            message: '邮箱输入错误'
                        }
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        different: {
                            field: 'username',
                            message: '密码不能和用户名一样'
                        }
                    }
                },
                passwordagain: {
                    validators: {
                        notEmpty: {
                            message: '内容不可为空'
                        },
                        identical: {
                            field: 'password',
                            message: '两次填写密码不一致'
                        }
                    }
                },
                phoneNumber: {
                    validators: {
                        notEmpty: {
                            message: '信息不能为空'
                        },

                        regexp: {
                            regexp: /^1(3|4|5|7|8)\d{9}$/,
                            message: '手机号码格式错误'
                        }
                    }
                }
            }
        }).on('err.form.fv', function(e) {
            console.log('error');

            // Active the panel element containing the first invalid element
            var $form         = $(e.target),
                validator     = $form.data('formValidation'),
                $invalidField = validator.getInvalidFields().eq(0),
                $collapse     = $invalidField.parents('.collapse');

            $collapse.collapse('show');
        });
    });
</script>
</body>
</html>