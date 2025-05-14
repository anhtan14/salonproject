import { Box, Button, TextField } from '@mui/material';
import { Formik } from 'formik';
import axios from '~/utils/api/axios';
import * as yup from 'yup';
import useMediaQuery from '@mui/material/useMediaQuery';
import Header from '../../components/Header';
import 'leaflet/dist/leaflet.css';
import L from 'leaflet';
import icon from 'leaflet/dist/images/marker-icon.png';
import iconShadow from 'leaflet/dist/images/marker-shadow.png';
import { useState, useEffect } from 'react';
import { MapContainer, TileLayer, useMapEvent, Marker, Popup } from 'react-leaflet';
import { useParams, useNavigate, Link } from 'react-router-dom';
import MenuItem from '@mui/material/MenuItem';
let defaultIcon = L.icon({
    iconUrl: icon,
    shadowUrl: iconShadow,
    iconAnchor: [16, 37],
});

L.Marker.prototype.options.icon = defaultIcon;
const Form = () => {
    const isNonMobile = useMediaQuery('(min-width:600px)');
    const [selectedPoint, setSelectedPoint] = useState(null);
    const [initialValues, setInitialValues] = useState({ phone: '', address: '', status: '' });
    let id = useParams();
    const navigate = useNavigate();
    const handleMapClick = (e) => {
        setSelectedPoint(e.latlng);
    };

    const MapClickHandler = () => {
        useMapEvent('click', handleMapClick);
        return null;
    };

    useEffect(() => {
        axios
            .get(`/branch/detail/${id.id}`)
            .then((res) => {
                const branch = res.data;
                setInitialValues({ phone: branch.phone, address: branch.address, status: branch.status * 1 });
                setSelectedPoint({
                    lat: branch.lat,
                    lng: branch.lng,
                });
            })
            .catch((error) => {
                console.log(error);
                if (error.response.status === 404) {
                    navigate('/404');
                }
            });
    }, [id.id,navigate]);

    const handleFormSubmit = (values) => {
        if (selectedPoint != null) {
            const formValues = {
                ...values,
                name: values.name,
                address: values.address,
                lat: selectedPoint.lat,
                lng: selectedPoint.lng,
                status: values.status,
            };

            axios
                .put(`/branch/${id.id}`, formValues)
                .then((res) => {
                    const persons = res.data;
                    console.log(persons);
                    navigate('/branch');
                })
                .catch((error) => console.log(error));
        } else {
            alert('Chưa chọn chi nhánh trên bản đồ');
        }
    };

    return (
        <Box m="20px">
            <Header title="Chỉnh sửa chi nhánh" />
            {initialValues.phone && initialValues.address && (
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
                                    label="Số điện thoại"
                                    onBlur={handleBlur}
                                    onChange={handleChange}
                                    value={values.phone}
                                    name="phone"
                                    error={!!touched.phone && !!errors.phone}
                                    helperText={touched.phone && errors.phone}
                                    sx={{ gridColumn: 'span 4' }}
                                />

                                <TextField
                                    fullWidth
                                    variant="filled"
                                    type="text"
                                    label="Địa chỉ"
                                    onBlur={handleBlur}
                                    onChange={handleChange}
                                    value={values.address}
                                    name="address"
                                    error={!!touched.address && !!errors.address}
                                    helperText={touched.address && errors.address}
                                    sx={{ gridColumn: 'span 4' }}
                                />
                                <TextField
                                    fullWidth
                                    variant="filled"
                                    // type="text"
                                    select
                                    label="Trạng thái"
                                    onBlur={handleBlur}
                                    onChange={handleChange}
                                    value={values.status}
                                    name="status"
                                    error={!!touched.branch && !!errors.branch}
                                    helperText={touched.branch && errors.branch}
                                    sx={{ gridColumn: 'span 4' }}
                                >
                                    <MenuItem key={1} value={0}>
                                        Đóng cửa
                                    </MenuItem>
                                    <MenuItem key={2} value={1}>
                                        Mở cửa
                                    </MenuItem>
                                </TextField>
                            </Box>
                            <div style={{ marginTop: '1rem', width: '100%' }}>
                                <MapContainer
                                    center={[15.979122033083634, 108.25241088867189]}
                                    style={{ height: 500 }}
                                    zoom={14}
                                >
                                    <TileLayer
                                        attribution="Salon"
                                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                                    ></TileLayer>
                                    {selectedPoint && (
                                        <Marker position={selectedPoint}>
                                            <Popup>Chi nhánh của bạn ở đây!</Popup>
                                        </Marker>
                                    )}
                                    <MapClickHandler />
                                </MapContainer>
                            </div>
                            <Box display="flex" justifyContent="end" mt="20px">
                                <Button type="submit" color="secondary" variant="contained">
                                    Cập nhật
                                </Button>
                                <Link to={'/branch'}>
                                    <Button type="submit" color="warning" variant="contained">
                                        Hủy
                                    </Button>
                                </Link>
                            </Box>
                        </form>
                    )}
                </Formik>
            )}
        </Box>
    );
};
const phoneRegExp = /^((\+[1-9]{1,4}[ -]?)|(\([0-9]{2,3}\)[ -]?)|([0-9]{2,4})[ -]?)*?[0-9]{3,4}[ -]?[0-9]{3,4}$/;
const checkoutSchema = yup.object().shape({
   phone: yup.string().matches(phoneRegExp, 'Số điện thoại không hợp lệ').required('Không được bỏ trống'),
    address: yup.string().required('Không được bỏ trống'),
});
// const initialValues = {
//     name: '',
//     address: '',
// };

export default Form;
