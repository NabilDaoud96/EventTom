import { ref } from 'vue';
import {baseApi} from "@/router/base-api";
import router from "@/router";

export function useRegistration() {
    const loading = ref(false);
    const error = ref('');

    const registerCustomer = async (formData) => {
        try {
            loading.value = true;
            error.value = '';

            const response = await baseApi.post('registration/customer', formData);
            router.push('/auth/login');

            return response.data;

        } catch (err) {
            if (err.response) {
                error.value = err.response.data.error
            }
        } finally {
            loading.value = false;
        }
    };

    const registerWithProvider = async (provider) => {
        try {
            loading.value = true;
            error.value = '';

            const response = await baseApi.get(`auth/${provider}`);
            return response.data;

        } catch (err) {
            if (err.response) {
                error.value = err.response.data.error
            }
        } finally {
            loading.value = false;
        }
    };

    return {
        loading,
        error,
        registerCustomer,
        registerWithProvider
    };
}