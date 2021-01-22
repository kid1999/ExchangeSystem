import Vue from 'vue';
export const globalBus = new Vue();


// 事件总线，用于组件之间互相调用，传输信息


// 调用方：
/**
 RefreshUser(data) {
    globalBus.$emit("RefreshUser", data);
}
 */


// 接收方：
/**
 mounted() {
    globalBus.$on("RefreshUser", (msg) => {
        // A发送来的消息
        console.info(msg);
        xxxx
    });
}
 **/