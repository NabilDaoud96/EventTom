
import api from "@/utils/axios-auth";
import {ref} from "vue";
export function useUser() {

    const loading = ref(false);
    const error = ref('');

    const getUser = async () => {
        try {
            loading.value = true;
            error.value = '';
            const response = await api.get('user/query/user');
            return response.data;

        } catch (err) {
            if (err.response) {
                error.value = err.response.data.error
            }
        } finally {
            loading.value = false;
        }
    };
    const getCurrentCustomer = async () => {
        try {
            loading.value = true;
            error.value = '';
            const response = await api.get('customers/user');
            return response.data;

        } catch (err) {
            if (err.response) {
                error.value = err.response.data.error
            }
        } finally {
            loading.value = false;
        }
    };
    const editUser = async (profile) => {
        try {
            loading.value = true;
            error.value = '';
            const response = await api.put('user/command/edit', profile);
            return response.data;
        } catch (err) {
            if (err.response) {
                error.value = err.response.data.message
            }
        } finally {
            loading.value = false;
        }
    };

    return {
        loading,
        error,
        getUser,
        getCurrentCustomer,
        editUser
    }
}