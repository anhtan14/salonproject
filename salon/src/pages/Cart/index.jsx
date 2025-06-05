import React, { useState, useEffect } from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import classNames from 'classnames/bind';
import moment from 'moment';
import { ToastContainer, toast } from 'react-toastify';
import L from 'leaflet';
import icon from 'leaflet/dist/images/marker-icon.png';
import iconShadow from 'leaflet/dist/images/marker-shadow.png';
import styles from './Cart.module.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useDispatch, useSelector } from 'react-redux';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import axios from '~/utils/api/axios';
import { removeToCart, addToCart } from '~/utils/store/authSlice';
import { useNavigate } from 'react-router-dom';

import avatarDefault from '~/assets/images/avatarDefault.jpg';
const cx = classNames.bind(styles);

function Cart() {
    const navigate = useNavigate();
    const [jsonData, setJsonData] = useState([]);
    const [staffs, setStaffs] = useState([]);
    const [event, setEvent] = useState({})
    const [times, setTimes] = useState([]);
    const [totalPrice, setTotalPrice] = useState(0);
    const [totalPriceBegin, setTotalPriceBegin] = useState(0);
    const user = useSelector((state) => state.auth.login?.currenUser);
    const dispatch = useDispatch();
    const deleteElement = (index, id) => {
        axios
            .post(`/booking/deleteDetail`, {
                serviceID: id,
                phone: user.phone,
            })
            .then(() => {
                dispatch(addToCart(-1));
                toast.success('Bạn đã xóa dịch vụ thành công', {
                    position: toast.POSITION.TOP_RIGHT,
                });
            })
            .catch((error) => console.log(error));
        const updatedElements = [...jsonData];
        updatedElements.splice(index, 1);
        setJsonData(updatedElements);
    };



    useEffect(() => {
        if (user) {
            axios
                .post(`/booking/listCart`, {
                    serviceID: 1,
                    phone: user.phone,
                })
                .then((res) => {
                    const cart = res.data;
                    // console.log(branch)
                    setJsonData(cart);
                })
                .catch((error) => console.log(error));





        } else {
            navigate('/login');
        }
    }, [user, navigate]);

    //staff
    // useEffect(() => {
    //     axios
    //         .get(`/booking/listStaff?branch=${selectedBranchId.id}`)
    //         .then((res) => {
    //             const staffs = res.data;
    //             // console.log(staffs);
    //             setStaffs(staffs);
    //         })
    //         .catch((error) => console.log(error));
    // }, []);
    //branch
    useEffect(() => {
        axios
            .get(`/branch`)
            .then((res) => {
                const branch = res.data;
                setBranches(branch);
            })
            .catch((error) => console.log(error));
    }, []);
    //BookServices
    const [branches, setBranches] = useState([]);
    const [dates, setDates] = useState([]);
    const handleClick = (daysToAdd) => {
        return new Promise((resolve) => {
            const nextDay = moment().add(daysToAdd, 'days').format('DD/MM/YYYY');
            // console.log('Next day:', nextDay);
            // Thực hiện các hành động khác với nextDay
            resolve(nextDay);
        });
    };
    useEffect(() => {
        const newTotalPrice = jsonData.reduce((total, element) => total + element.price, 0);
        setTotalPrice(newTotalPrice);
        setTotalPriceBegin(newTotalPrice);
    }, [jsonData]);
    useEffect(() => {
        const fetchDates = async () => {
            const promises = [0, 1, 2, 3, 4, 5, 6].map((daysToAdd) => handleClick(daysToAdd));
            const updatedDates = await Promise.all(promises).then((results) =>
                results.map((date, index) => ({ id: index + 1, date })),

            );
            setDates(updatedDates);
            console.log(updatedDates[0].date)
        };

        fetchDates();
    }, []);

    const [active, setActive] = useState(false);
    const [selectedBranchId, setSelectedBranchId] = useState(null);
    const [discount, setDiscount] = useState(0);
    const [selectedTimeId, setSelectedTimeId] = useState(null);
    const [selectedDateId, setSelectedDateId] = useState(null);
    const [selectedStaff, setSelectedStaff] = useState(null);

    const handleSelectChange = (event) => {
        const selectID = parseInt(event.target.value);
        const selected = staffs.find((staff) => staff.id === selectID);
        setSelectedStaff(selected);
        if (selectID !== 0) {
            setActive(true);
        } else {
            setActive(false);
        }
        axios.post('/booking/event')
            .then((res) => {
                if (res.data == 'not') {
                    setEvent(null)
                }
                else {
                    setEvent(res.data)
                }

                console.log(res.data)


            })
    };

    const handleBranchesChange = (event) => {
        const selectedID = parseInt(event.target.value);
        const selected = branches.find((branch) => branch.id === selectedID);
        setSelectedBranchId(selected);
        console.log(selected);
        axios
            .get(`/booking/listStaff?branch=${selected.id}`)
            .then((res) => {
                const staffs = res.data;
                // console.log(staffs);
                setStaffs(staffs);
            })
            .catch((error) => console.log(error));


    };
    const handleDateClick = (id) => {
        setSelectedDateId(id);

        const date1 = dates[id - 1].date
        axios
            .post(`/booking/listTime`, {
                staffId: selectedStaff.id,
                date: dates[id - 1].date,
            })
            .then((res) => {
                const time = res.data;
                setTimes(time);
            })
            .catch((error) => console.log(error));

        axios
            .post(`/booking/discount?date=${date1}`)
            .then((res) => {
                if (res.data != 'not') {
                    const discount = res.data;
                    setTotalPrice(totalPriceBegin)

                    if (discount !== 0) {
                        setTotalPrice(totalPrice - (totalPrice / 100 * discount))
                        console.log(discount)
                        setDiscount(discount)
                    } else setDiscount(0)
                }
            })
            .catch((error) => console.log(error));
    };



    const handleTimesClick = (id) => {
        setSelectedTimeId(id);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!user) {
            navigate('/login');
        } else {
            if (selectedBranchId && selectedStaff && selectedDateId && selectedTimeId) {
                if (isChecked) {
                    axios
                        .post(`/checkout/create-payment`, {
                            date: dates[selectedDateId - 1].date,
                            totalPrice: totalPrice,
                            nhanvien: selectedStaff.id,
                            user: user.phone,
                            time: selectedTimeId,
                            branch: selectedBranchId.id,
                            bankCode: 'NCB',
                        })
                        .then((res) => {
                            dispatch(removeToCart());
                            toast.success('Bạn đã đặt lịch thành công', {
                                position: toast.POSITION.TOP_RIGHT,
                            });
                            window.location.href = res.data.url;
                        });
                } else {
                    axios
                        .post(`/booking/book`, {
                            date: dates[selectedDateId - 1].date,
                            totalPrice: totalPrice,
                            nhanvien: selectedStaff.id,
                            user: user.phone,
                            time: selectedTimeId,
                            branch: selectedBranchId.id,
                            bankCode: 'NCB',
                        })
                        .then((res) => {
                            if (res.data === 'ss') {
                                dispatch(removeToCart());
                                toast.success('Bạn đã đặt lịch thành công', {
                                    position: toast.POSITION.TOP_RIGHT,

                                });
                                navigate('/')
                            } else if (res.data === 'full') {
                                toast.error('Lịch của nhân viên bạn chọn không khả dụng, vui lòng chọn khung giờ khác', {
                                    position: toast.POSITION.TOP_RIGHT,
                                });
                            }
                            else {
                                toast.error('Bạn còn đơn hàng chưa được xác nhận', {
                                    position: toast.POSITION.TOP_RIGHT,
                                });
                            }
                        });
                }
            } else {
                toast.error('Vui lòng chọn đầy đủ các mục !!!', {
                    position: toast.POSITION.TOP_RIGHT,
                });
            }
        }
    };

    const [isChecked, setIsChecked] = useState(false);

    const position = [15.977456962147246, 108.2627979201717];
    let defaultIcon = L.icon({
        iconUrl: icon,
        shadowUrl: iconShadow,
        iconAnchor: [16, 37],
    });

    L.Marker.prototype.options.icon = defaultIcon;

    return (
        <div>
            {jsonData.length === 0 || !user ? (
                <h1>Bạn chưa đặt sản phẩm!</h1>
            ) : (
                <>
                    <h1>Giỏ Hàng</h1>
                    <table className={cx('table-element')}>
                        <tbody>
                            {jsonData.map((element, index) => (
                                <tr key={index}>
                                    <th className={cx('imgaes')}>
                                        <img src={element.img} alt="abc" />
                                    </th>
                                    <th>
                                        <h2>{element.tittle}</h2>

                                        <div> {element.name}</div>
                                    </th>
                                    <th>{element.price.toLocaleString('en-US')} VNĐ</th>
                                    <th>
                                        <div
                                            onClick={() => deleteElement(index, element.id)}
                                            style={{ cursor: 'pointer' }}
                                        >
                                            <FontAwesomeIcon icon={faTrash}></FontAwesomeIcon>
                                        </div>
                                    </th>
                                </tr>
                            ))}
                        </tbody>
                    </table>

                    <div className={cx('booking-service')}>
                        <div className={cx('booking-information')}>
                            <h3>ĐẶT LỊCH</h3>
                            <div className={cx('branch-staff-booking')}>
                                <p className={cx('branch-title')}>Chọn salon (*):</p>
                                <select onChange={handleBranchesChange}>
                                    <option value="0">- Vui lòng chọn chi nhánh -</option>
                                    {branches.map((branch) => (
                                        <option
                                            key={branch.id}
                                            value={branch.id}
                                        // onClick={() => handleBranchesClick(branch.id)}
                                        >
                                            {branch.address}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className={cx('branch-staff-booking')}>
                                <p>Chọn nhân viên (*):</p>
                                <select onChange={handleSelectChange}>
                                    <option value="0">- Vui lòng chọn nhân viên -</option>
                                    {staffs.map((staff) => (
                                        <option className="" key={staff.id} value={staff.id}>
                                            {staff.name}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className={cx('date-booking')}>
                                <p className={cx('branch-title')}>Chọn ngày (*):</p>
                                <div className={cx('date-time')}>
                                    {dates.map((date) =>
                                        active !== true ? (
                                            <button disabled key={date.id}>
                                                {date.date}
                                            </button>
                                        ) : (
                                            <button
                                                className={cx({ selectted: date.id === selectedDateId })}
                                                key={date.id}
                                                onClick={() => handleDateClick(date.id)}
                                            >
                                                {date.date}
                                            </button>
                                        ),
                                    )}
                                </div>
                                {event != null ? (
                                    <p className={cx('event')}>{event.date} giảm giá {event.discount}%</p>
                                ) : ("")}
                                <p className={cx('branch-title')}>Chọn giờ (*):</p>
                                <div className={cx('status-time')}>
                                    <div>
                                        <div style={{ backgroundColor: '#000' }}></div>
                                        <span>Đã chọn</span>
                                    </div>
                                    <div>
                                        <div style={{ backgroundColor: '#fff', border: '1px solid #333' }}></div>
                                        <span>Chưa chọn</span>
                                    </div>
                                </div>
                                <div className={cx('date-time')}>
                                    {times.map((data) => (
                                        <>
                                            {active !== true ? (
                                                <button disabled key={data.id}>
                                                    {data.time}
                                                </button>
                                            ) : (
                                                <button
                                                    className={cx({ selectted: data.id === selectedTimeId })}
                                                    key={data.id}
                                                    onClick={() => handleTimesClick(data.id)}
                                                >
                                                    {data.time}
                                                </button>
                                            )}
                                        </>
                                    ))}
                                </div>

                                <div className="checkbox-">
                                    <div className="form-check">
                                        <input
                                            className="form-check-input"
                                            type="checkbox"
                                            value=""
                                            id="flexCheckDisabled"
                                            onChange={() => setIsChecked((prev) => !prev)}
                                        />
                                        <label className="form-check-label" htmlFor="flexCheckDisabled">
                                            Thanh toán Online
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div className={cx('total-price')}>
                                <p className={cx('total-price-title')}>Tổng tiền:</p>{' '}
                                <span>{totalPrice.toLocaleString('en-US')}</span> VNĐ
                                {discount !== 0 ? (
                                    <p className={cx('event')}>giảm giá {discount}%</p>
                                ) : (<></>)}
                            </div>

                            <button className={cx('submit-booking')} type="submit" onClick={handleSubmit}>
                                ĐẶT LỊCH
                            </button>
                            <ToastContainer />
                        </div>
                        <div className={cx('booking-information')}>
                            <div className={cx('booking-staffs-information')}>
                                <h3>THÔNG TIN NHÂN VIÊN BẠN ĐÃ CHỌN</h3>
                                {selectedStaff && (
                                    <div>{selectedStaff.img != null ?
                                        (<img className={cx('staff-img')} src={selectedStaff.img} />
                                        ) : (
                                            <img className={cx('staff-img')} src={avatarDefault} />
                                        )
                                    }
                                        <div>
                                            <p>
                                                <strong>{selectedStaff.name}</strong>
                                            </p>
                                            <p>Ngày sinh: {selectedStaff.birthday}</p>
                                            <p>Email: {selectedStaff.email}</p>
                                            <p>Số điện thoại: {selectedStaff.phone}</p>
                                        </div>
                                    </div>
                                )}
                            </div>
                            <MapContainer
                                center={position}
                                zoom={5}
                                scrollWheelZoom={false}
                                style={{ height: '48%', width: '100%', bottom: '0' }}
                            >
                                <TileLayer
                                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                                />
                                {selectedBranchId && (
                                    <Marker position={{ lat: selectedBranchId.lat, lng: selectedBranchId.lng }}>
                                        <Popup>
                                            A pretty CSS3 popup. <br /> Easily customizable.
                                        </Popup>
                                    </Marker>
                                )}
                            </MapContainer>
                        </div>
                    </div>
                </>
            )}
        </div>
    );
}

export default Cart;
