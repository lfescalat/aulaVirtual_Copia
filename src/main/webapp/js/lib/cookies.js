const Cookies = {
    set: (key, value, expiration) => {
        key = [encodeURIComponent(key) + "=" + encodeURIComponent(value)]
        expiration && ("expiry" in expiration && ("number" === typeof expiration.expiry && (expiration.expiry = new Date(1E3 * expiration.expiry + +new Date)),
                key.push("expires=" + expiration.expiry.toGMTString())),
                "domain" in expiration && key.push("domain=" + expiration.domain),
                "path" in expiration && key.push("path=" + expiration.path),
                "secure" in expiration && expiration.secure && key.push("secure"))
        document.cookie = key.join("; ")
    },
    get: (key, bool) => {
        const arr = []
        for (let cookies = document.cookie.split(/; */), i = 0; i < cookies.length; i++) {
            var cookie = cookies[i].split("=")
            cookie[0] === encodeURIComponent(key) && arr.push(decodeURIComponent(cookie[1].replace(/\+/g, "%20")))
        }

        return bool ? arr : arr[0]
    },
    clear: (b, c) => {
        c || (c = {});
        c.expiry = -86400;
        Cookies.set(b, "", c)
    }
};