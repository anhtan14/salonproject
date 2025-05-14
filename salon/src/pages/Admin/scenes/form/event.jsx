import { Box, Button, TextField } from '@mui/material';
import axios from '~/utils/api/axios';
import { Formik } from 'formik';
import * as yup from 'yup';
import useMediaQuery from '@mui/material/useMediaQuery';
import Header from '../../components/Header';
import InputAdornment from '@mui/material/InputAdornment';
import { useState } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import * as React from 'react';
import moment from 'moment';
const Form = () => {
    function isImageFile(file) {
        const acceptedImageTypes = ['image/jpeg', 'image/png', 'image/gif'];
        const acceptedExtensions = ['.jpg', '.jpeg', '.png', '.gif'];

        // Check MIME type
        if (acceptedImageTypes.includes(file.type)) {
            return true;
        }

        // Check file extension
        const fileName = file.name;
        const fileExtension = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
        if (acceptedExtensions.includes(fileExtension)) {
            return true;
        }

        return false;
    }
    const isNonMobile = useMediaQuery('(min-width:600px)');
    const handleFormSubmit = (values, { resetForm }) => {
        console.log(values.date)
        if (imageBase64 !== '') {
            const formValues = {
                ...values,
                discount: values.price,
                content: values.description,
                status: 0,
                img: imageBase64,
                date: values.date
            };
            
            axios
                .post(`/event`, formValues)
                .then((res) => {
                    toast.success('Tạo event thành công!', {
                        position: 'top-right',
                        autoClose: 5000,
                        hideProgressBar: false,
                        closeOnClick: true,
                        pauseOnHover: true,
                        draggable: true,
                        progress: undefined,
                        theme: 'light',
                    });
                    resetForm();
                    setImageBase64('');
                })
                .catch((error) => console.log(error));
        } else {
            toast.warning('Bạn chưa chọn ảnh!', {
                position: 'top-right',
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: 'light',
            });
        }
    };
    const [imageBase64, setImageBase64] = useState('');

    function handleImageUpload(event) {
        const file = event.target.files[0];
        if (isImageFile(file)) {
            const reader = new FileReader();

            reader.onloadend = () => {
                const base64String = reader.result;
                setImageBase64(base64String);
            };

            reader.readAsDataURL(file);
        } else {
            setImageBase64('');
            toast.warning('Đây không phải là ảnh!', {
                position: 'top-right',
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: 'light',
            });
        }
    }
    return (
        <>
            <Box m="20px">
                <Header title="Tạo event" />

                <Formik onSubmit={handleFormSubmit} initialValues={initialValues} validationSchema={checkoutSchema}>
                    {({ values, errors, touched, handleBlur, handleChange, handleSubmit }) => (
                        <form onSubmit={handleSubmit}>
                            <Box
                                display="grid"
                                gap="30px"
                                gridTemplateColumns="repeat(4, minmax(0, 1fr))"
                                sx={{
                                    '& > div': { gridColumn: isNonMobile ? undefined : 'span 4' },
                                }}
                            >
                                <TextField
                                    fullWidth
                                    variant="filled"
                                    type="text"
                                    label="Discount"
                                    onBlur={handleBlur}
                                    onChange={handleChange}
                                    value={values.price}
                                    name="price"
                                    error={!!touched.price && !!errors.price}
                                    helperText={touched.price && errors.price}
                                    sx={{ gridColumn: 'span 4' }}
                                />

                                <TextField
                                    fullWidth
                                    variant="filled"
                                    type="text"
                                    label="Mô tả"
                                    onBlur={handleBlur}
                                    onChange={handleChange}
                                    value={values.description}
                                    name="description"
                                    error={!!touched.description && !!errors.description}
                                    helperText={touched.description && errors.description}
                                    sx={{ gridColumn: 'span 4' }}
                                />
                                <TextField
                                    fullWidth
                                    variant="filled"
                                    type="date"
                                    label="Ngày"
                                    onBlur={handleBlur}
                                    onChange={handleChange}
                                    value={values.date}
                                    name="date"
                                    error={!!touched.date && !!errors.date}
                                    helperText={touched.date && errors.date}
                                    sx={{ width: '200px' }}
                                    InputProps={{
                                        startAdornment: <InputAdornment position="start"></InputAdornment>,
                                    }}
                                />
                                <TextField
                                    fullWidth
                                    variant="filled"
                                    type="file"
                                    label="Ảnh"
                                    onBlur={handleBlur}
                                    onChange={handleImageUpload}
                                    sx={{ gridColumn: 'span 4' }}
                                    inputProps={{
                                        accept: '.jpg,.jpeg,.png',
                                    }}
                                    InputProps={{
                                        startAdornment: <InputAdornment position="start"></InputAdornment>,
                                    }}
                                />
                                {imageBase64 && (
                                    <img src={imageBase64} alt="Selected" style={{ width: '400px', height: '400px' }} />
                                )}
                            </Box>
                            <Box display="flex" justifyContent="end" mt="20px">
                                <Button type="submit" color="secondary" variant="contained">
                                    Tạo event
                                </Button>
                            </Box>
                        </form>
                    )}
                </Formik>
            </Box>
            <ToastContainer />
        </>
    );
};

const checkoutSchema = yup.object().shape({
    price: yup.number().required('Không hợp lệ'),
    description: yup.string().required('Không hợp lệ'),
    date: yup.date().min(new Date(), 'Không được chọn nhỏ hơn ngày hôm nay').required('Không được bỏ trống'),
});
const initialValues = {
    price: '',
    description: '',
    date: '',
};

export default Form;
