import React from 'react';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
} from 'chart.js';
import { Line } from 'react-chartjs-2';
import { Box, TextField, Button, InputAdornment, MenuItem, Typography } from '@mui/material';
import axios, { BASE_URL } from '~/utils/api/axios';
import { Formik } from 'formik';
import * as yup from 'yup';
import { useEffect } from 'react';
import { useState } from 'react';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

function Chart() {
    const [chartLine, setChartLine] = useState(null);
    const [error, setError] = useState('');
    const [branchData, setBranchData] = useState([]);
    const [url, setUrl] = useState(BASE_URL+`/dashboard/chart/download`);
   
    useEffect(() => {
        axios
            .get(`/branch`)
            .then((res) => {
                const branch = res.data;
                setBranchData(branch);
            })
            .catch((error) => console.log(error));
    }, []);
    useEffect(() => {
        axios
            .get(`/dashboard/chart`)
            .then((response) => {
                const chart = response.data;
                setChartLine({
                    labels: chart.map((element) => element.date),
                    datasets: [
                        {
                            label: 'Doanh thu',
                            data: chart.map((element) => element.total),
                            borderColor: 'rgb(255, 99, 132)',
                            backgroundColor: 'rgba(255, 99, 132, 0.5)',
                        },
                    ],
                });
            })
            .catch((error) => {
                console.error(error);
            });
    }, []);
   
    const options = {
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            },
            title: {
                display: true,
                text: 'Biểu đồ doanh thu.',
            },
        },
    };
    const handleFormSubmit = (values) => {

        axios
            .post(`/dashboard/chart`, { dateStart: values.dateStart, dateEnd: values.dateEnd, type: values.status, branch:values.branch  })
            .then((response) => {
                const chart = response.data;
                if (chart === 'year') {
                    setError('Năm phải khác nhau!');
                } else if (chart === 'month') {
                    setError('Tháng phải khác nhau và năm phải giống nhau!');
                } else if (chart === 'day') {
                    setError('Tháng phải giống nhau!');
                } else {
                    setError('');
                    setUrl(BASE_URL+`/dashboard/chart/${values.dateStart}/${values.dateEnd}/${values.status}/${values.branch}`)
                    setChartLine({
                        labels: chart.map((element) => element.date),
                        datasets: [
                            {
                                label: 'Doanh thu',
                                data: chart.map((element) => element.total),
                                borderColor: 'rgb(255, 99, 132)',
                                backgroundColor: 'rgba(255, 99, 132, 0.5)',
                            },
                        ],
                    });
                }
            })
            .catch((error) => {
                console.error(error);
            });
    };
    return (
        <Box m="20px">
            <Formik onSubmit={handleFormSubmit} initialValues={initialValues} validationSchema={checkoutSchema}>
                {({ values, errors, touched, handleBlur, handleChange, handleSubmit }) => (
                    <form onSubmit={handleSubmit}>
                        <Box display="grid" gap="30px" gridTemplateColumns="repeat(4, minmax(0, 1fr))">
                            <TextField
                                fullWidth
                                variant="filled"
                                type="date"
                                label="Ngày bắt đầu"
                                onBlur={handleBlur}
                                onChange={handleChange}
                                value={values.dateStart}
                                name="dateStart"
                                error={!!touched.dateStart && !!errors.dateStart}
                                helperText={touched.dateStart && errors.dateStart}
                                sx={{ width: '200px' }}
                                InputProps={{
                                    startAdornment: <InputAdornment position="start"></InputAdornment>,
                                }}
                            />

                            <TextField
                                // fullWidth
                                variant="filled"
                                type="date"
                                label="Ngày kết thúc "
                                onBlur={handleBlur}
                                onChange={handleChange}
                                value={values.dateEnd}
                                name="dateEnd"
                                error={!!touched.dateEnd && !!errors.dateEnd}
                                helperText={touched.dateEnd && errors.dateEnd}
                                sx={{ width: '200px' }}
                                InputProps={{
                                    startAdornment: <InputAdornment position="start"></InputAdornment>,
                                }}
                            />

                            <TextField
                                fullWidth
                                variant="filled"
                                // type="text"
                                select
                                label="Phương thức duyệt"
                                onBlur={handleBlur}
                                onChange={handleChange}
                                value={values.status}
                                name="status"
                                error={!!touched.status && !!errors.status}
                                helperText={touched.status && errors.status}
                            >
                                <MenuItem value={0}>Chọn phương thức</MenuItem>
                                <MenuItem value={1}>Năm</MenuItem>
                                <MenuItem value={2}>Tháng</MenuItem>
                                <MenuItem value={3}>Ngày</MenuItem>
                            </TextField>
                            <TextField
                                fullWidth
                                variant="filled"
                                // type="text"
                                select
                                label="Chi nhánh"
                                onBlur={handleBlur}
                                onChange={handleChange}
                                value={values.branch}
                                name="branch"
                                sx={{ gridColumn: 'span 4' }}
                            >
                                <MenuItem value={0}>Tất cả chi nhánh</MenuItem>
                                {branchData.map((values) => (
                                    <MenuItem key={values.id} value={values.id}>
                                        {values.address}
                                    </MenuItem>
                                ))}
                            </TextField>
                        </Box>
                        <Typography variant="h4" color={'red'}>
                            {error}
                        </Typography>
                        <Box display="flex" justifyContent="start" mt="20px">
                            <Button type="submit" color="secondary" variant="contained" sx={{ marginRight: '20px' }}>
                                Duyệt
                            </Button>
                            <Button  color="secondary" variant="contained"  href={url} >
                                Tải báo cáo
                            </Button>
                        </Box>
                    </form>
                )}
            </Formik>
            {chartLine ? <Line options={options} data={chartLine} /> : <p>Đang load dữ liệu...</p>}
        </Box>
    );
}
const checkoutSchema = yup.object().shape({
    dateEnd: yup
        .date()
        .max(new Date(), 'Không được chọn quá ngày hôm nay')
        .min('01/01/1923', 'ngày không hợp lệ')
        .required('Không được bỏ trống'),
    dateStart: yup
        .date()
        .test('dateStart', 'Ngày bắt đầu phải nhỏ hơn ngày kết thúc', function (value) {
            const { dateEnd } = this.parent;
            return !dateEnd || value < dateEnd;
        })
        .min('01/01/1923', 'Ngày không hợp lệ')
        .required('Không được bỏ trống'),
    status: yup.number().notOneOf([0], 'Phải chọn phương thức'),
});

const initialValues = {
    dateStart: new Date().toISOString().split('T')[0],
    dateEnd: new Date().toISOString().split('T')[0],
    status: 0,
    branch: 0,
};
export default Chart;
