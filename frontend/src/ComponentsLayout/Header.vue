<script setup lang="ts"></script>

<template>
    <header>
        <div id="header-nav">
            <router-link to="/">
                <img id="logo" src="../assets/logo.png" alt="로고이미지">
            </router-link>
            
            <nav>
                <ul>
                    <li>
                        <router-link to="/manage-M">회원 관리</router-link>
                    </li>
                    <li>
                        <router-link to="/manage-SA">합격자 관리</router-link>
                    </li>
                </ul>
            </nav>
        </div>

        <div id="util">
            <router-link to="/login">로그인</router-link>
            <router-link to="/join">회원가입</router-link>
        </div>

    </header>
</template>

<style scoped>
header {
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;

    padding: 20px 0px;
}

header a {
    color:#333;
    transition: all 0.3s;
}

header a:hover {
    color:var(--mainColor);
}

#header-nav {
    display: flex;
    align-items: center;
    gap: 200px;
}

    #header-nav #logo {
        width: 132px;
        height: auto;
    }

    #header-nav nav ul {
        display: flex;
        gap: 120px;
    }

    #header-nav nav ul li a {
        font-size: 16px;
        font-weight: 600;
    }

#util {
    display: flex;
    gap:24px;
}

    #util a {
        font-size: 14px;
        font-weight: 400;
    }
</style>