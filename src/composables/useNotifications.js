
import api from "@/utils/axios-auth";
import {ref} from "vue";
export function useNotifications() {

    const loading = ref(false);
    const error = ref('');

    const getUserNotifications = async () => {
        try {
            loading.value = true;
            error.value = '';
            const response = await api.get('notifications');
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
        getUserNotifications
    }
}