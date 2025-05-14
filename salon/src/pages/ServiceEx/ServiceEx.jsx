import { Link, useNavigate } from 'react-router-dom';
import ReactPaginate from 'react-paginate';
import { MdKeyboardArrowRight, MdKeyboardArrowLeft } from 'react-icons/md';
import { useState } from 'react';
import { Breadcrumbs } from '~/pages/Breadcrumbs';
import { ServiceExItem } from '~/pages/ServiceExItem';
import './ServiceEx.css';

function ServiceEx() {
    const mock = [
        {
            id: 1,
            title: 'Long-top Undercut Mái dài vuốt bồng',
            imgUrl: 'https://product.hstatic.net/1000181446/product/hairstyle2.png',
        },
        {
            id: 2,
            title: 'Long-top Undercut Mái dài vuốt bồng V2',
            imgUrl: '//product.hstatic.net/1000181446/product/img_2_c2f4f6de22874a0ab78932ec3185fe27_medium.jpg',
        },
        {
            id: 3,
            title: 'Long-top Undercut Mái dài vuốt bồng v3',
            imgUrl: 'https://product.hstatic.net/1000181446/product/hairstyle4_7ff186f065ac425fb1c193a0eae5e2df.png',
        },
        {
            id: 4,
            title: 'Middle-part Ngôi giữa hiện đại',
            imgUrl: 'https://product.hstatic.net/1000181446/product/img_8_2630e34f2648494385564e071b2abd82_medium.jpg',
        },
        {
            id: 5,
            title: 'Middle-part Ngôi giữa hiện đại v2',
            imgUrl: 'https://product.hstatic.net/1000181446/product/hairstyle1_b9f8047902924ebb9bba7b4128e7b684_medium.png',
        },
        {
            id: 6,
            title: 'Quiff-Undercut với mái bồng',
            imgUrl: 'https://product.hstatic.net/1000181446/product/img_5_3ab41517669a41ed8e09a001c18b3b3d_medium.jpg',
        },
        {
            id: 7,
            title: 'Quiff-Undercut với mái bồng v2',
            imgUrl: 'https://product.hstatic.net/1000181446/product/hairstyle3_14129c9e00f74a7eb6831f16601e0f3e_medium.png',
        },
        {
            id: 8,
            title: 'Side-part Ngôi lệch hiện đại',
            imgUrl: 'https://product.hstatic.net/1000181446/product/img_7_1216e82d9a854ddd973981a31747ec26_medium.jpg',
        },
        {
            id: 9,
            title: 'Side-part Ngôi lệch hiện đại v2',
            imgUrl: 'https://product.hstatic.net/1000181446/product/img_3_31f37eda96964075946476411fab5db3_medium.jpg',
        },
        {
            id: 10,
            title: 'Side-part Ngôi lệch hiện đại v3',
            imgUrl: 'https://product.hstatic.net/1000181446/product/hairstyle1_f19d13469c07429894441c9cfb42ef3b_medium.png',
        },
        {
            id: 11,
            title: 'Side-swept Undercut mái vuốt lệch bên',
            imgUrl: 'https://product.hstatic.net/1000181446/product/img_6_3713863c074e42a2b3dca5a3c6dd9280_medium.jpg',
        },
        {
            id: 12,
            title: 'Side-swept Undercut mái vuốt lệch bên v2',
            imgUrl: 'https://product.hstatic.net/1000181446/product/hairstyle1_f4ca186ad33040b89f99317a5a78d7d7.png',
        },
        {
            id: 13,
            title: 'Side-swept Undercut mái vuốt lệch bên v3',
            imgUrl: 'https://product.hstatic.net/1000181446/product/img_1_527374b9955945d6b2342cac1c80fc58_medium.jpg',
        },
        {
            id: 14,
            title: 'Slick-back Undercut vuốt ngược',
            imgUrl: 'https://product.hstatic.net/1000181446/product/hairstyle1_medium.png',
        },
        {
            id: 15,
            title: 'Slick-back Undercut vuốt ngược v2',
            imgUrl: 'https://product.hstatic.net/1000181446/product/hairstyle3_6fd43e3b23654978b0667a1d0635066f_medium.png',
        },
        {
            id: 16,
            title: 'Slick-back Undercut vuốt ngược v3',
            imgUrl: 'https://product.hstatic.net/1000181446/product/hairstyle2_a3bf32c93f8044cf817b2680e32026b0_medium.png',
        },
        {
            id: 17,
            title: 'Slick-back Undercut vuốt ngược v4',
            imgUrl: 'https://product.hstatic.net/1000181446/product/hairstyle2_22a4dd494fe54e9eb171e3ede8b201c0_medium.png',
        },
    ];
    const [services] = useState(mock);
    const navigate = useNavigate();
    const onClickService = (id) => () => {
        navigate(`/service/${id}/details`);
    };

    const [currentPage, setCurrentPage] = useState(0);
    const itemsPerPage = 6;

    const pageCount = Math.ceil(services.length / itemsPerPage);
    const offset = currentPage * itemsPerPage;
    const currentServices = services.slice(offset, offset + itemsPerPage);

    const handlePageChange = (selectedPage) => {
        setCurrentPage(selectedPage.selected);
    };

    return (
        <div>
            <section
                className="header"
                style={{
                    '--bg-url': `url(${'https://theme.hstatic.net/1000181446/1000235350/14/image_breadcrumb_bg.png?v=1737'})`,
                }}
            >
                <h1 className="heading">MẪU DỊCH VỤ</h1>
                <Breadcrumbs className="breadcrumbs">
                    <Link className="breadcrumb-link" to="/">
                        Trang chủ
                    </Link>
                    <Link className="breadcrumb-link" to="/serviceExample">
                        Mẫu dịch vụ
                    </Link>
                </Breadcrumbs>
            </section>
            <section className="body">
                <h2 className="heading">TRỞ THÀNH QUÝ ÔNG LỊCH LÃM CÙNG SUPLO</h2>
                <p className="sub-heading">
                    Chúng tôi hạnh phúc khi mỗi ngày đem đến cho phái mạnh toàn cầu sự tự tin tỏa sáng, sức khoẻ, niềm
                    vui thư giãn; bằng những sản phẩm, dịch vụ chăm sóc sức khỏe, da mặt chuyên nghiệp - tạo kiểu tóc
                    thời trang - gói gọn trong quy trình khoa học 30phút (không phải chờ đợi lâu) với giá thành rẻ nhất
                    Việt Nam.
                </p>
                <div className="service-list">
                    {currentServices.map((service) => (
                        <ServiceExItem key={service.id} onClick={onClickService(service.id)} {...service} />
                    ))}
                </div>
                <div className="paginate-wrapper">
                    <ReactPaginate
                        previousLabel={<MdKeyboardArrowLeft size={24} />}
                        nextLabel={<MdKeyboardArrowRight size={24} />}
                        breakLabel={'...'}
                        breakClassName={'break-me'}
                        pageCount={pageCount}
                        marginPagesDisplayed={1}
                        pageRangeDisplayed={3}
                        onPageChange={handlePageChange}
                        containerClassName={'paginateContainer'}
                        activeClassName={'active'}
                    />
                </div>
            </section>
        </div>
    );
}

export default ServiceEx;
