import {UserService} from "@/services/UserService";
import {IUser} from "@/interfaces/IUser";

const userService = new UserService();

export default {
    /**
     * Reactive form fields and validation errors.
     */
    data() {
        return {
            email: "",
            password: "",
            showPassword: false,
            errors: {
                email: "",
                password: "",
            }
        };
    },

    methods: {
        /**
         * Validates the email format and required field, returns an error message or empty string
         */
        validateEmail() {
            if (!this.email) {
                return "Email is required.";
            }
            if (!/^\S+@\S+\.\S+$/.test(this.email)) {
                return "Email is not valid.";
            }
            return "";
        },

        /**
         * Checks if the password meets the required rules and returns an error message or empty string
         */
        validatePassword() {
            if (!this.password) {
                return "Password is required.";
            }
            if (this.password.length < 8) {
                return "Password must be at least 8 characters.";
            }
            return "";
        },

        /**
         * Runs all validation methods, updates error fields and returns true if the form is valid
         */
        validateFields() {
            this.errors.email = this.validateEmail();
            this.errors.password = this.validatePassword();

            return (
                !this.errors.email &&
                !this.errors.password
            );
        },

        /**
         * Validates form and sends the register request to the backend.
         * Redirects to login on success, maps backend errors to fields.
         */
        async submitForm() {
            if (!this.validateFields()) {
                return;
            }

            try {
                const user: IUser = await userService.login(this.email, this.password);

                localStorage.setItem("user", JSON.stringify(user));
                console.log("successfully logged in");

                this.$router.push("/");

            } catch (err: any) {
                //TODO: Map backend errors to fields
                console.error(err);
            }
        }
    }
}