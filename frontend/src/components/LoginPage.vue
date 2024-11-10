<template>
    <div class="login-page">
        <h2>Login</h2>
        <form @submit.prevent="handleLogin">
            <div>
                <label for="username">Username:</label>
                <input type="text" v-model="username" id="username" required />
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" v-model="password" id="password" required />
            </div>
            <button type="submit">Login</button>
        </form>
        <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </div>
</template>

<script>
import { loginUser } from '../services/apiService';

export default {
    data() {
        return {
            username: '',
            password: '',
            errorMessage: null
        };
    },
    methods: {
        async handleLogin() {
            try {
                const result = await loginUser(this.username, this.password);
                // 로그인 성공 처리 (예: 토큰 저장 및 페이지 이동)
                console.log('Login successful:', result);
                this.$router.push('/dashboard'); // 로그인 후 대시보드로 이동
            } catch (error) {
                this.errorMessage = 'Login failed. Please try again.';
            }
        }
    }
};
</script>

<style scoped>
.login-page {
    max-width: 400px;
    margin: auto;
}

.error {
    color: red;
}
</style>