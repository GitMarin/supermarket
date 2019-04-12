Vue.prototype.$bdmsg = function(msgData){
    switch(msgData.type){
        case "toast":
            this.$message({
                type:msgData.msgType?msgData.msgType:"info",
                duration:msgData.duration?msgData.duration:3000,
                iconClass:msgData.iconClass?msgData.iconClass:null,
                showClose: msgData.showClose?msgData.showClose:false,
                message: msgData.message,
                onClose:function(){
                    if(typeof msgData.onClose=="function"){
                        msgData.onClose();
                    }
                }
            });
            break;
        case "alert":
            this.$notify({
                type: msgData.msgType?msgData.msgType:"info",
                position:msgData.position?msgData.position:"top-right",
                duration:msgData.duration?msgData.duration:0,
                iconClass:msgData.iconClass?msgData.iconClass:null,
                showClose: msgData.showClose?msgData.showClose:true,
                title: msgData.title,
                message: msgData.message,
                onClose:function(){
                    if(typeof msgData.onClose=="function"){
                        msgData.onClose();
                    }
                }
            });
            break;
        case "confirm":
            debugger
            this.$msgbox({
                iconClass:msgData.iconClass?msgData.iconClass:null,
                showClose: msgData.showClose?msgData.showClose:true,
                title: msgData.title,
                message: msgData.message,
                showCancelButton:msgData.showCancelButton?msgData.showCancelButton:false,
                showConfirmButton:msgData.showConfirmButton?msgData.showConfirmButton:true,
                cancelButtonText:msgData.cancelButtonText?msgData.cancelButtonText:"取消",
                confirmButtonText:msgData.confirmButtonText?msgData.confirmButtonText:"确定",
                beforeClose:function(action, instance, done){
                    switch(action){
                        case "confirm":
                            if(typeof msgData.confirmBeforeClose=="function"){
                                if(msgData.confirmBeforeClose()){
                                    done();
                                }
                            }
                            break;
                        default:
                            if(typeof msgData.cancelBeforeClose=="function"){
                                if(msgData.cancelBeforeClose()){
                                    done();
                                }
                            }
                    }

                },
                callback:function(action, instance){
                    switch(action){
                        case "confirm":
                            if(typeof msgData.confirmCallback=="function"){
                                msgData.confirmCallback();
                            }
                            break;
                        default:
                            if(typeof msgData.cancelCallback=="function"){
                                msgData.cancelCallback();
                            }
                    }
                }
            });
            break;
        case "page":
            window.sessionStorage.setItem('errMsg',JSON.stringify({title: msgData.title,
                message: msgData.message}));
            window.location.href = msgData.href?msgData.href:'../../errorMsg.html';
            break;
        default:
    }
};



