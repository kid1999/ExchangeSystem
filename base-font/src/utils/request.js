import axios from 'axios'
import { Loading, Message  } from 'element-ui'    // 这里我是使用elementUI的组件来给提示
import router from '@/router'

let loadingInstance = null;    // 加载全局的loading

const instance = axios.create({    //创建axios实例，在这里可以设置请求的默认配置
    timeout: 10000, // 设置超时时间10s
    baseURL: process.env.NODE_ENV === 'production' ? '' : '/api'   //根据自己配置的反向代理去设置不同环境的baeUrl
});
// 文档中的统一设置post请求头。下面会说到post请求的几种'Content-Type'
instance.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

let httpCode = {        //这里我简单列出一些常见的http状态码信息，可以自己去调整配置
    400: '请求参数错误',
    401: '权限不足, 请重新登录',
    403: '服务器拒绝本次访问',
    404: '请求资源未找到',
    500: '内部服务器错误',
    501: '服务器不支持该请求中使用的方法',
    502: '网关错误',
    504: '网关超时'
}

/** 添加请求拦截器 **/
instance.interceptors.request.use(config => {
    config.headers['token'] = sessionStorage.getItem('token') || '';
    loadingInstance = Loading.service({       // 发起请求时加载全局loading，请求失败或有响应时会关闭
        spinner: 'el-icon-loading',
        text: '拼命加载中...'
    });
    if (config.method === 'get') { // 添加时间戳参数，防止浏览器（IE）对get请求的缓存
        config.params = {
            ...config.params,
            t: new Date().getTime()
        }
    }
    // 在这里：可以根据业务需求可以在发送请求之前做些什么:例如我这个是导出文件的接口，因为返回的是二进制流，所以需要设置请求响应类型为blob，就可以在此处设置。
    if (config.url.includes('pur/contract/export')) {
        config.headers['responseType'] = 'blob'
    }
    // 我这里是文件上传，发送的是二进制流，所以需要设置请求头的'Content-Type'
    if (config.url.includes('pur/contract/upload')) {
        config.headers['Content-Type'] = 'multipart/form-data'
    }
    return config
}, error=> {
    // 对请求错误做些什么
    return Promise.reject(error)
});

/** 添加响应拦截器  **/
instance.interceptors.response.use(response => {
    loadingInstance.close()
    if (response.data.status === 200) {     // 响应结果里的status: ok是我与后台的约定，大家可以根据实际情况去做对应的判断
        return Promise.resolve(response.data)
    } else {
        Message({
            message: response.data.message,
            type: 'error'
        })
        return Promise.reject(response.data.message)
    }
}, error => {
    loadingInstance.close();
    if (error.response) {
        // 根据请求失败的http状态码去给用户相应的提示
        let tips = error.response.status in httpCode ? httpCode[error.response.status] : error.response.data.message;
        Message({
            message: tips,
            type: 'error'
        })
        if (error.response.status === 401) {    // token或者登陆失效情况下跳转到登录页面，根据实际情况，在这里可以根据不同的响应错误结果，做对应的事。这里我以401判断为例
            router.push({
                path: `/login`
            })
        }
        return Promise.reject(error)
    } else {
        Message({
            message: '请求超时, 请刷新重试',
            type: 'error'
        })
        return Promise.reject(new Error('请求超时, 请刷新重试'))
    }
})

/* 统一封装get请求 */
export const get = (url, params, config = {}) => {
    return new Promise((resolve, reject) => {
        instance({
            method: 'get',
            url,
            params,
            ...config
        }).then(response => {
            resolve(response)
        }).catch(error => {
            reject(error)
        })
    })
}

/* 统一封装post请求  */
export const post = (url, data, config = {}) => {
    return new Promise((resolve, reject) => {
        instance({
            method: 'post',
            url,
            data,
            ...config
        }).then(response => {
            resolve(response)
        }).catch(error => {
            reject(error)
        })
    })
};

/* 统一封装put请求  */
export const put = (url, data, config = {}) => {
    return new Promise((resolve, reject) => {
        instance({
            method: 'put',
            url,
            data,
            ...config
        }).then(response => {
            resolve(response)
        }).catch(error => {
            reject(error)
        })
    })
};
