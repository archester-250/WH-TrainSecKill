import request from '@/utils/request'

// 用户登录
export function register(username, password) {
    const data = {
        username,
        password
    }
    return request({
        url: '/user/user/register',
        method: 'post', 
        data: data
    })
}
