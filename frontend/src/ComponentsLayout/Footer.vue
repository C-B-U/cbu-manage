<script setup lang="ts"></script>

<template>
    <footer>
        <router-link to="/">
            <img id="footer-logo" src="../assets/logo.png" alt="로고이미지">
        </router-link>
            
        <div class="footer-info-area">
            <p class="footer-info">회장 : 정우혁 ㅣ이메일 : 000@tukorea.ac.kr ㅣ 번호 : 010-1234-5678</p>
            <p class="footer-info">부회장 : 정우혁 ㅣ이메일 : 000@tukorea.ac.kr ㅣ 번호 : 010-1234-5678</p>
        </div>

        <div class="social-area">
            <a href="#">인스타그램</a>
            <a href="#">한국공학대학교</a>
        </div>

        <hr>
        <p class="copyright">Copyright ⓒ 2024 씨부엉 All rights reserved.</p>

    </footer>
</template>

<style scoped>
footer {
    width: 100%;
    padding: 32px var(--entirePadding);

    background-color: #EEEEEE;
}

    #footer-logo {
        width: 132px;
        height: auto;
        mix-blend-mode: darken;
    }

footer .footer-info-area {
    margin: 32px 0px;
}

    .footer-info-area p {
        text-align: center;
        font-size: 14px;
        color: #333;
    }

.social-area {
    display: flex;
    gap:16px;
    justify-content: center;
}

    .social-area a {
        font-size: 14px;
        color:#333;
    }

footer hr {
    border:none;
    height:1px;
    background-color: #CCCCCC;
    margin:12px 0px;
}

.copyright {
    font-size: 12px;
    color:#888;
}
</style>