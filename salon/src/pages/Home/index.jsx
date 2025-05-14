import React from 'react';
import Slideshow from './slideshow';
import classNames from 'classnames/bind';
import styles from './Home.module.scss';
import slideshow1 from '~/assets/images/slideshow1.jpg';
import slideshow2 from '~/assets/images/slideshow2.jpg';
import slideshow3 from '~/assets/images/slideshow3.jpg';
import axios from '~/utils/api/axios';
import { useEffect, useState } from 'react';
import avatarDefault from '~/assets/images/avatarDefault.jpg';
import MyModal from '~/event/modal';

const cx = classNames.bind(styles);
function Home() {
    const slideshowImages = [slideshow1, slideshow2, slideshow3];
    const [topUsers, setTopUsers] = useState([]);
    const [mostBookedServices, setMostBookedServices] = useState([]);

    useEffect(() => {
        axios
            .get(`/service/most-booked`)
            .then((response) => {
                setMostBookedServices(response.data);
            })
            .catch((error) => {
                console.error('Error fetching most booked services:', error);
            });
    }, []);

    useEffect(() => {
        axios
            .get(`/users/top-users`)
            .then((response) => {
                setTopUsers(response.data);
            })
            .catch((error) => {
                console.error('Error fetching top users:', error);
            });
    }, []);

    function mapRole(role) {
        switch (role) {
            case 'ROLE_STAFF':
                return 'Chuyên gia';
            default:
                return role;
        }
    }

    return (
        <>
            <MyModal />
            <Slideshow slides={slideshowImages} />
            <div className={cx('about')}>
                <div className="container">
                    <div className="row align-items-center">
                        <div className="col-lg-5 col-md-6">
                            <div className={cx('about-img')}>
                                <img src={process.env.PUBLIC_URL + '/img/about.jpg'} alt="about" />
                            </div>
                        </div>
                        <div className="col-lg-7 col-md-6">
                            <div className={cx('section-header')} style={{ textAlign: 'center' }}>
                                <h2>TRẢI NGHIỆM SALON ĐẲNG CẤP VIỆT NAM</h2>
                            </div>
                            <div className={cx('about-text')}>
                                <p>
                                    Thành lập từ năm 2019, SalonSpace vẫn luôn đứng vững suốt 4 năm trong ngành thẩm mỹ
                                    Việt cạnh tranh khốc liệt và đem đến vẻ đẹp hoàn hảo cho hàng triệu khách hàng.
                                </p>
                                <p>
                                    Quy tụ đội ngũ chuyên gia uy tín hàng đầu trong nước . Mỗi một dịch vụ sẽ có ekip
                                    chuyên gia cố vấn và bác sĩ chuyên môn để giúp khách hàng đạt hiệu quả tối đa.
                                </p>
                                <p>
                                    Chúng tôi hạnh phúc khi mỗi ngày được đem đến cho mọi người sự tự tin tỏa sáng, sức
                                    khỏe và niềm vui bằng những dịch vụ chăm sóc sức khỏe, da mặt chuyên nghiệp với giá
                                    phù hợp.
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div className={cx('service')}>
                <div className="container">
                    <div className={cx('section-header')} style={{ textAlign: 'center' }}>
                        <h2>Dịch vụ tốt nhất cho bạn</h2>
                    </div>
                    <div className="row">
                        <div className="col-lg-4 col-md-6">
                            <div className={cx('service-item')}>
                                <div className={cx('service-img')}>
                                    <img src={process.env.PUBLIC_URL + '/img/service-1.jpg'} alt="service" />
                                </div>
                                <h3>Tạo mẫu tóc</h3>
                                <p>Phù hợp với mọi gương mặt khác nhau, mang đến sự sự trẻ trung, hiện đại.</p>
                            </div>
                        </div>
                        <div className="col-lg-4 col-md-6">
                            <div className={cx('service-item')}>
                                <div className={cx('service-img')}>
                                    <img src={process.env.PUBLIC_URL + '/img/service-2.jpg'} alt="service" />
                                </div>
                                <h3>Cạo mặt</h3>
                                <p>Cạo mặt giúp da sáng mịn để bạn luôn hoàn hảo trước buổi hẹn quan trọng.</p>
                            </div>
                        </div>
                        <div className="col-lg-4 col-md-6">
                            <div className={cx('service-item')}>
                                <div className={cx('service-img')}>
                                    <img src={process.env.PUBLIC_URL + '/img/service-3.jpg'} alt="service" />
                                </div>
                                <h3>Vệ sinh tai</h3>
                                <p>Vệ sinh tai giúp bảo vệ đôi tai của bạn sạch sẽ và an toàn hơn</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div className={cx('price')}>
                <div className="container">
                    <div className={cx('section-header')} style={{ textAlign: 'center' }}>
                        <h2>Những Dịch vụ hàng đầu</h2>
                    </div>
                    <div className="row">
                        {mostBookedServices.map((service, index) => (
                            <div className="col-lg-3 col-md-4 col-sm-6" key={index}>
                                <div className={cx('price-item')}>
                                    <div className={cx('price-img')}>
                                        {/* You can replace this with an appropriate image source */}
                                        <img src={service[0]} alt="price" />
                                    </div>
                                    <div className={cx('price-text')}>
                                        <h2 className={cx('price-text-name')}>{service[1]}</h2>
                                        <h3>{service[2]}VNĐ</h3>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </div>

            <div className={cx('team')}>
                <div className="container">
                    <div className={cx('section-header')} style={{ textAlign: 'center' }}>
                        <h2>ĐỘI NGŨ CHUYÊN GIA GIÀU KINH NGHIỆM</h2>
                    </div>
                    <div className="row">
                        {topUsers.map((user, index) => (
                            <div className="col-lg-3 col-md-6" key={index}>
                                <div className={cx('team-item')}>
                                    <div className={cx('team-img')}>
                                        <img src={user[0] ? user[0] : avatarDefault} alt="Team" />
                                    </div>
                                    <div className={cx('team-text')}>
                                        <h2>{user[1]}</h2>
                                        <p className={cx('team-role')}>{mapRole(user[2])}</p>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </>
    );
}

export default Home;
