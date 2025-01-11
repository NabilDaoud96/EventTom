import api from "@/utils/axios-auth";
import {ref} from "vue";
export function useTickets() {

    const loading = ref(false);
    const error = ref('');

    const getUserTickets = async () => {
        try {
            loading.value = true;
            error.value = '';
            const response = await api.get('tickets/query/user');
            console.log(response.data)
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
        getUserTickets
    }
}