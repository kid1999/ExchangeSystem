// 每个模块都应该有自己的接口文件去统一管理api
import { get, post } from '@/utils/request'

export const getTest = (params) => get('/user/info', params);

export const postTest = (params) => post('/user/login', params);