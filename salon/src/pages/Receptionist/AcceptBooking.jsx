import React, { useState, useEffect } from 'react';
import { Box, Button } from '@mui/material';
import { DataGrid, viVN } from '@mui/x-data-grid';
import axios from '~/utils/api/axios';
import Swal from 'sweetalert2';
import { useSelector } from 'react-redux';
// import { Link } from 'react-router-dom';
import { isReceptionist } from '~/utils/jwt';
import { useNavigate } from 'react-router-dom';
function AcceptBooking() {
    const [teamData, setTeamData] = useState([]);
    const user = useSelector((state) => state.auth.login?.currenUser);
    const navigate = useNavigate();
    useEffect(() => {
        if (user) {
            axios
                .get(`receptionist/booking/${user.phone}`)
                .then((res) => {
                    const user = res.data;
                    setTeamData(user);
                })
                .catch((error) => console.log(error));
        }
    }, [user]);
    const handleStatusClick = (id) => {
        Swal.fire({
            html: `<h4>Xác nhận đơn hàng đã được hoàn thành!</h4>`,
            // input: 'number',
            showCancelButton: true,
            confirmButtonText: 'Xác nhận',
            cancelButtonText: 'Đóng',
            showLoaderOnConfirm: true,
            confirmButtonColor: '#4caf50',
            cancelButtonColor: ' #D3D3D3',
            allowOutsideClick: false,
            preConfirm: (code) => {
                return axios
                    .get(`/receptionist/status/${id}`)
                    .then((res) => {
                        if (id === res.data) {
                            const updatedTeamData = teamData.map((booking) => {
                                if (booking.id === id) {
                                    return { ...booking, status: 1 };
                                }
                                return booking;
                            });
                            setTeamData(updatedTeamData);
                        }
                    })
                    .catch((error) => console.log(error));
            },
        });
    };
    const handleAcceptClick = (id) => {
        Swal.fire({
            html: `<h4>Xác nhận đơn hàng!</h4>`,
            // input: 'number',
            showCancelButton: true,
            confirmButtonText: 'Xác nhận',
            cancelButtonText: 'Đóng',
            showLoaderOnConfirm: true,
            confirmButtonColor: '#4caf50',
            cancelButtonColor: ' #D3D3D3',
            allowOutsideClick: false,
            preConfirm: (code) => {
                return axios
                    .get(`/receptionist/accept/${id}`)
                    .then((res) => {
                        if (id === res.data) {
                            const updatedTeamData = teamData.map((booking) => {
                                if (booking.id === id) {
                                    return { ...booking, status: 2 };
                                }
                                return booking;
                            });
                            setTeamData(updatedTeamData);
                        }
                    })
                    .catch((error) => console.log(error));
            },
        });
    };
    const handlePaymentClick = (id) => {
        Swal.fire({
            html: `<h4>Xác nhận đơn hàng đã được thánh toán!</h4>`,
            // input: 'number',
            showCancelButton: true,
            confirmButtonText: 'Xác nhận',
            cancelButtonText: 'Đóng',
            showLoaderOnConfirm: true,
            confirmButtonColor: '#4caf50',
            cancelButtonColor: ' #D3D3D3',
            allowOutsideClick: false,
            preConfirm: (code) => {
                return axios
                    .get(`/receptionist/payment/${id}`)
                    .then((res) => {
                        if (id === res.data) {
                            const updatedTeamData = teamData.map((booking) => {
                                if (booking.id === id) {
                                    return { ...booking, payment: 1 };
                                }
                                return booking;
                            });
                            setTeamData(updatedTeamData);
                        }
                    })
                    .catch((error) => console.log(error));
            },
        });
    };
    const columns = [
        { field: 'id', headerName: 'ID' },
        {
            field: 'name',
            headerName: 'Tên',
            flex: 1,
            renderCell: ({ row }) => {
                if (row.user) {
                    return row.user.phone;
                } else {
                    return 'Khách hàng';
                }
            },
        },
        {
            field: 'date',
            headerName: 'Ngày',
            type: 'text',
            headerAlign: 'left',
            align: 'left',
            flex: 1,
            renderCell: ({ row }) => {
                const timestamp = row.time;
                if (timestamp) {
                    return row.date + ' | ' + row.time.times;
                } else {
                    return row.date;
                }
            },
        },
        {
            field: 'status',
            headerName: 'Trạng thái',
            flex: 1,
            renderCell: ({ row }) => {
                return row.status === 0 ? 'Đang chờ' :  row.status === 1 ?'Đã hoàn thành' : row.status === 2 ? 'Đã xác nhận' : 'Hủy';
            },
        },
        {
            field: 'payment',
            headerName: 'Thanh toán',
            flex: 1,
            renderCell: ({ row }) => {
                return row.payment === 0 ? 'Chưa thanh toán' : 'Đã thanh toán';
            },
        },
        {
            field: 'totalPrice',
            headerName: 'Tổng tiền',
            flex: 1,
        },
        {
            field: 'actions',
            headerName: 'Thanh toán',
            flex: 1,
            renderCell: ({ row }) => {
                return row.payment === 0 ? (
                    <Box>
                        <Button
                            color="secondary"
                            variant="contained"
                            sx={{ fontFamily: 'Lora, serif' }}
                            onClick={() => handlePaymentClick(row.id)}
                        >
                            Thanh toán
                        </Button>
                    </Box>
                ) : null;
            },
        },
        {
            field: 'actions2',
            headerName: 'Hoàn thành',
            flex: 1,
            renderCell: ({ row }) => {
                return row.status === 0 ? (
                    <Box>
                        <Button
                            color="warning"
                            variant="contained"
                            sx={{ fontFamily: 'Lora, serif' }}
                            onClick={() => handleAcceptClick(row.id)}
                        >
                            Xác nhận
                        </Button>
                    </Box>
                ) : row.status === 2 ? (
                    <Box>
                        <Button
                            color="warning"
                            variant="contained"
                            sx={{ fontFamily: 'Lora, serif' }}
                            onClick={() => handleStatusClick(row.id)}
                        >
                            Hoàn thành
                        </Button>
                    </Box>
                ) : null;
            },
        },
    ];
    return user && isReceptionist(user.accessToken) ? (
        <>
            <div style={{ width: '100%', height: '100%' }}>
                <Box
                    m="40px"
                    height="90vh"
                    sx={{
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
                        sx={{ wordWrap: 'break-word' }}
                    />
                </Box>
            </div>
        </>
    ) : (
        navigate('/accessDeny')
    );
}

export default AcceptBooking;
