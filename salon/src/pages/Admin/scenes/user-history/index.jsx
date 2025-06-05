import React, { useState } from 'react';
import { Box, Button } from '@mui/material';
import { DataGrid, viVN } from '@mui/x-data-grid';
import Header from '../../components/Header';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import axios from '~/utils/api/axios';
import { useEffect } from 'react';
import { useSelector } from 'react-redux';
import './user-history.scss';
import { log } from 'react-modal/lib/helpers/ariaAppHider';

const HistoryBooking = () => {
    const [teamData, setTeamData] = useState([]);
    const user = useSelector((state) => state.auth.login?.currenUser);
    useEffect(() => {
        console.log(user);
        if (user && user.phone) {
            console.log('Fetching data for user:', user.phone); // Debugging
            axios
                .get(`/bookings/history/${user.phone}`)
                .then((res) => {
                    const bookingHistory = res.data;
                    console.log('Fetched data:', bookingHistory); // Debugging
                    setTeamData(bookingHistory);
                })
                .catch((error) => console.log('Error fetching data:', error)); // Debugging
        }
    }, [user]);

    const payment = (row) => {
        axios.post(`/checkout/re-create-payment?bookingid=${row}`
        ).then((res) => {
            window.location.href = res.data.url;
        })
    }

    const cancel = (row) => {
        axios.post(`/booking/cancel?bookingid=${row}`)
            .then((res) => {
                if (res.data === 'ok') {
                    toast.success('Hủy đặt lịch thành công!', {
                        position: "top-right",
                        autoClose: 3000,
                        hideProgressBar: false,
                        closeOnClick: true,
                        pauseOnHover: true,
                        draggable: true,
                    });
                    // Refresh the booking history\
                    axios
                        .get(`/bookings/history/${user.phone}`)
                        .then((res) => {
                            const bookingHistory = res.data;
                            setTeamData(bookingHistory);
                        })
                        .catch((error) => console.log('Error fetching data:', error));
                } else {
                    // Show error message
                    console.error('Hủy không thành công');
                }
            })
            .catch((error) => {
                // Handle error
                console.error('Error hủy:', error);
            });
    }

    //danhgias
    const columns = [
        { field: 'id', headerName: 'ID', flex: 0.5 },
        {
            field: 'date',
            headerName: 'Ngày tháng',
            type: 'number',
            flex: 1.5,
            headerAlign: 'left',
            align: 'left',
        },
        {
            field: 'discount',
            headerName: 'Giảm giá',
            headerAlign: 'left',
            align: 'left',
            flex: 1,
        },
        {
            field: 'status',
            headerName: 'Trạng thái',
            type: 'text',
            headerAlign: 'left',
            align: 'left',
            flex: 1.5,
            renderCell: ({ row }) => {
                return (
                    <span>
                        {row.status === 0
                            ? 'Đang chờ'
                            : row.status === 1
                                ? 'Đã hoàn thành'
                                : row.status === 2
                                    ? 'Đã chấp nhận'
                                    : 'Hủy'}
                    </span>
                );
            },
        },
        {
            field: 'payment',
            headerName: 'Trạng thái thanh toán',
            flex: 2.5,
            type: 'text',
            headerAlign: 'left',
            align: 'left',
            renderCell: ({ row }) => {
                return <span>{row.payment === 0 ? 'Chưa thanh toán' : 'Đã thanh toán'}</span>;
            },
        },
        {
            field: 'time',
            headerName: 'Thời gian',
            type: 'text',
            headerAlign: 'left',
            align: 'left',
            flex: 1,
            renderCell: ({ row }) => {
                return (
                    <div style={{ wordWrap: 'break-word' }}>
                        <div>{row.time?.times}</div>
                    </div>
                );
            },
        },
        {
            field: 'totalPrice',
            headerName: 'Tổng tiền',
            flex: 1.5,
            headerAlign: 'left',
            align: 'left',
        },
        {
            field: 'nhanvienName',
            headerName: 'Tên nhân viên',
            flex: 2,
            headerAlign: 'left',
            align: 'left',
        },

        // {
        //     field: 'user', // Use a new field name
        //     headerName: 'Số điện thoại người dùng',
        //     flex: 1,
        //     headerAlign: 'left',
        //     align: 'left',
        //     renderCell: ({ row }) => {
        //         return (
        //             <div style={{ wordWrap: 'break-word' }}>
        //                 <div>{row.user.phone}</div>
        //             </div>
        //         );
        //     },
        // },
        {
            field: 'branch',
            headerName: 'Chi nhánh',
            flex: 3,
            renderCell: ({ row }) => {
                return (
                    <div style={{ wordWrap: 'break-word' }}>
                        <div>{row.branch.address}</div>
                    </div>
                );
            },
        },
        {
            field: 'thanhtoan',
            headerName: 'Thanh toán đơn hàng',
            flex: 2.5,
            type: 'text',
            headerAlign: 'left',
            align: 'left',
            renderCell: ({ row }) => {
                return (row.payment === 0 && row.status != 1 && row.status != 3) ? (
                    <Box>
                        <Button
                            color="secondary"
                            variant="contained"
                            sx={{ fontFamily: 'Lora, serif' }}
                            onClick={() => payment(row.id)}
                        >
                            Thanh toán
                        </Button>
                        <Button
                            color="error"
                            variant="contained"
                            sx={{ fontFamily: 'Lora, serif' }}
                            onClick={() => cancel(row.id)}
                        >
                            Hủy
                        </Button>
                    </Box>
                ) : null;
            },
        },
    ];

    return (
        <>
            <div className="container">
                <Box m="10px">
                    <Header title="Lịch sử giao dịch" />
                    <span className="history-subtitle">Quản lý lịch sử giao dịch</span>
                    <Box
                        m="40px 0 0 0"
                        height="100vh"
                        width="100%"
                        sx={{
                            minWidth: 1200,
                            '& .MuiDataGrid-root': {
                                border: '1px solid #ccc',
                                fontSize: '14px',
                            },
                            '& .MuiDataGrid-cell': {
                                borderBottom: '1px solid #333',
                                backgroundColor: '#fbfbfb1',
                            },
                            '& .name-column--cell': {
                                backgroundColor: '#fbfbfb1',
                                border: '1px solid #ccc',
                            },
                            '& .MuiDataGrid-columnHeaders': {
                                backgroundColor: '#000',
                                color: '#fff',
                                border: '1px solid #ccc',
                            },
                            '& .MuiDataGrid-virtualScroller': {
                                backgroundColor: '#fbfbfb1',
                                border: '1px solid #ccc',
                            },
                            '& .MuiDataGrid-footerContainer': {
                                backgroundColor: '#000',
                                border: '1px solid #ccc',
                                '& p': {
                                    fontSize: '14px',
                                    color: '#fff',
                                },
                            },
                            '& .MuiCheckbox-root': {
                                color: '#fbfbfb1 !important',
                            },
                            '& .MuiToolbar-root': {
                                fontSize: '14px',
                                color: '#fff',
                            },
                        }}
                    >
                        <DataGrid
                            rows={teamData}
                            columns={columns}
                            localeText={viVN.components.MuiDataGrid.defaultProps.localeText}
                        />
                    </Box>
                </Box>
                <ToastContainer />
            </div>
        </>
    );
};

export default HistoryBooking;
