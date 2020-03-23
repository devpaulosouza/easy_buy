export const getToken = () => {
    return window.localStorage.getItem('token');
}

export const getRole = () => {
    return window.localStorage.getItem('role');
}

export const clearStorage = () => {
    console.log('limpando...')
    window.localStorage.removeItem('token');
    window.localStorage.removeItem('role');
}


export const isAuthenticated = () => {
    console.log(getToken())
    return !!getToken()
}
