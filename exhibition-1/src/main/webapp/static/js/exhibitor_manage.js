layui.use('form', function () {
    var element = layui.element;
    var form = layui.form;

    //点击左边显示右边对应内容
    $(' li .info').click(function () {
        idx = $(this).index(' li .info');

        var selectItm = $('.text').eq(idx).children("div").children("iframe");

        selectItm.attr('src',selectItm.attr("src"));//点击后刷新frame

        $('.text').eq(idx).show();
        $('.text').not($('.text').eq(idx)).hide();
    });
//$('.z_item').click(function(){
//		idx=$(this).index('.z_item');
//	
//		$('.text').eq(idx).show();
//		$('.text').not($('.text').eq(idx)).hide();
//	});
    //监听提交
    form.on('submit(formDemo)', function (data) {
        layer.msg(JSON.stringify(data.field));
        return false;
    });
});
layui.use('element', function () {
    var element = layui.element;

});
layui.use('upload', function () {
    var upload = layui.upload;
    //多图片上传
    upload.render({
        elem: '#test2'
        , url: '/upload/'
        , multiple: true
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#demo2').append('<img src="' + result + '" alt="' + file.name + '" class="layui-upload-img">')
            });
        }
        , done: function (res) {
            //上传完毕
        }
    });
    //普通图片上传
    var uploadInst = upload.render({
        elem: '#test1'
        , url: '/upload/'
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#demo1').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            //如果上传失败
            if (res.code > 0) {
                return layer.msg('上传失败');
            }
            //上传成功
        }
        , error: function () {
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function () {
                uploadInst.upload();
            });
        }
    });
});
//分页
layui.use(['laypage', 'layer'], function () {
    var laypage = layui.laypage
        , layer = layui.layer;

    //总页数低于页码总数
    laypage.render({
        elem: 'demo0'
        , count: 50 //数据总数
    });
    laypage.render({
        elem: 'demo1'
        , count: 50 //数据总数
    });
});

layui.use(['form', 'layedit'], function () {
    var form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit

    //自定义验证规则
    form.verify({
        pass: [/(.+){6,12}$/, '密码必须6到12位']
        , content: function (value) {
            layedit.sync(editIndex);
        }
    });

////监听提交
//form.on('submit(demo1)', function(data){
//  layer.alert(JSON.stringify(data.field), {
//    title: '最终的提交信息'
//  })
//  return false;
//});
//

});


