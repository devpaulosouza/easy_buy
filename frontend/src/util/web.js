export const getToken = () => {
    return window.localStorage.getItem('token');
}

export const getRole = () => {
    return window.localStorage.getItem('role');
}

export const getUserId = () => {
    return window.localStorage.getItem('userId');
}

export const clearStorage = () => {
    console.log('limpando...')
    window.localStorage.removeItem('token');
    window.localStorage.removeItem('role');
    window.localStorage.removeItem('userId');
}


export const isAuthenticated = () => {
    console.log(getToken())
    return !!getToken()
}
