import classNames from 'classnames/bind';
import styles from './Message.module.scss';
import logoMessage from '~/assets/images/logoMessage.png';
import { useState } from 'react';
const cx = classNames.bind(styles);
function Message() {
    const [isShow, setIsShow] = useState(false);

    return (
        <>
            <img src={logoMessage} className={cx('img-logo')} onClick={() => setIsShow(!isShow)} alt='avatar'/>
            {/* {isShow && ( */}
               
                <div className={cx('content')} style={isShow ? { right: '5%' } : {}}>
                    <div className="container p-0">
                        <div className="card">
                            <div className="row g-0">
                                <div className="col-12 col-lg-5 col-xl-5 border-right">
                                    {/* <a href="#" className="list-group-item list-group-item-action border-0"> */}
                                    <div className="badge bg-success float-right">5</div>
                                    <div className="d-flex align-items-start">
                                        <img
                                            src="https://bootdey.com/img/Content/avatar/avatar5.png"
                                            className="rounded-circle mr-1"
                                            alt="Vanessa Tucker"
                                            width="40"
                                            height="40"
                                        />
                                        <div className="flex-grow-1 ml-3">
                                            Vanessa Tucker
                                            <div className="small">
                                                <span className="fas fa-circle chat-online"></span> Online
                                            </div>
                                        </div>
                                    </div>
                                    {/* </a> */}
                                    {/* <a href="#" className="list-group-item list-group-item-action border-0"> */}
                                    <div className="badge bg-success float-right">2</div>
                                    <div className="d-flex align-items-start">
                                        <img
                                            src="https://bootdey.com/img/Content/avatar/avatar2.png"
                                            className="rounded-circle mr-1"
                                            alt="William Harris"
                                            width="40"
                                            height="40"
                                        />
                                        <div className="flex-grow-1 ml-3">William Harris</div>
                                    </div>
                                    {/* </a> */}
                                    {/* <a href="#" className="list-group-item list-group-item-action border-0"> */}
                                    <div className="d-flex align-items-start">
                                        <img
                                            src="https://bootdey.com/img/Content/avatar/avatar3.png"
                                            className="rounded-circle mr-1"
                                            alt="Sharon Lessman"
                                            width="40"
                                            height="40"
                                        />
                                        <div className="flex-grow-1 ml-3">Sharon Lessman</div>
                                    </div>
                                    {/* </a> */}
                                    <hr className="d-block d-lg-none mt-1 mb-0" />
                                </div>
                                <div className="col-12 col-lg-7 col-xl-7">
                                    <div className={cx('px-4', 'px-4', 'border-bottom', 'd-none', 'd-lg-block')}>
                                        <div className="d-flex align-items-center py-1">
                                            <div className="position-relative">
                                                <img
                                                    src="https://bootdey.com/img/Content/avatar/avatar3.png"
                                                    className="rounded-circle mr-1"
                                                    alt="Sharon Lessman"
                                                    width="40"
                                                    height="40"
                                                />
                                            </div>
                                            <div className="flex-grow-1 pl-3">
                                                <strong>Sharon Lessman</strong>
                                            </div>
                                        </div>
                                    </div>

                                    <div className="position-relative">
                                        <div className={cx('chat-messages', 'p-4')}>
                                            <div className={cx('chat-message-right', 'pb-4')}>
                                                <div className="flex-shrink-1 bg-light rounded py-2 px-3 mr-3">
                                                    Lorem ipsum dolor sit amet, vis erat denique in, dicunt prodesset te
                                                    vix.
                                                    <div
                                                        className="text-muted small text-nowrap mt-2 "
                                                        style={{ textAlign: 'end' }}
                                                    >
                                                        2:33 am
                                                    </div>
                                                </div>
                                            </div>

                                            <div className={cx('chat-message-left', 'pb-4')}>
                                                <div>
                                                    <img
                                                        src="https://bootdey.com/img/Content/avatar/avatar3.png"
                                                        className="rounded-circle mr-1"
                                                        alt="Sharon Lessman"
                                                        width="40"
                                                        height="40"
                                                    />
                                                    <div className="text-muted small text-nowrap mt-2">2:34 am</div>
                                                </div>
                                                <div
                                                    className={cx(
                                                        'message-sent',
                                                        'flex-shrink-1',
                                                        'rounded',
                                                        'py-2',
                                                        'px-3',
                                                        'ml-3',
                                                    )}
                                                >
                                                    Sit meis deleniti eu, pri vidit meliore docendi ut, an eum erat
                                                    animal commodo.
                                                </div>
                                            </div>

                                            <div className={cx('chat-message-right', 'mb-4')}>
                                                <div className="flex-shrink-1 bg-light rounded py-2 px-3 mr-3">
                                                    Cum ea graeci tractatos.
                                                    <div
                                                        className="text-muted small text-nowrap mt-2 "
                                                        style={{ textAlign: 'end' }}
                                                    >
                                                        2:33 am
                                                    </div>
                                                </div>
                                            </div>

                                            <div className={cx('chat-message-left', 'pb-4')}>
                                                <div>
                                                    <img
                                                        src="https://bootdey.com/img/Content/avatar/avatar3.png"
                                                        className="rounded-circle mr-1"
                                                        alt="Sharon Lessman"
                                                        width="40"
                                                        height="40"
                                                    />
                                                    <div className="text-muted small text-nowrap mt-2">2:36 am</div>
                                                </div>
                                                <div
                                                    className={cx(
                                                        'message-sent',
                                                        'flex-shrink-1',
                                                        'rounded',
                                                        'py-2',
                                                        'px-3',
                                                        'ml-3',
                                                    )}
                                                >
                                                    Sed pulvinar, massa vitae interdum pulvinar, risus lectus porttitor
                                                    magna, vitae commodo lectus mauris et velit. Proin ultricies
                                                    placerat imperdiet. Morbi varius quam ac venenatis tempus.
                                                </div>
                                            </div>

                                            <div className={cx('chat-message-left', 'pb-4')}>
                                                <div>
                                                    <img
                                                        src="https://bootdey.com/img/Content/avatar/avatar3.png"
                                                        className="rounded-circle mr-1"
                                                        alt="Sharon Lessman"
                                                        width="40"
                                                        height="40"
                                                    />
                                                    <div className="text-muted small text-nowrap mt-2">2:37 am</div>
                                                </div>
                                                <div
                                                    className={cx(
                                                        'message-sent',
                                                        'flex-shrink-1',
                                                        'rounded',
                                                        'py-2',
                                                        'px-3',
                                                        'ml-3',
                                                    )}
                                                >
                                                    Cras pulvinar, sapien id vehicula aliquet, diam velit elementum
                                                    orci.
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div className={cx('py-3', 'flex-grow-0', 'px-4', 'border-top')}>
                                        <div className="input-group">
                                            <input
                                                type="text"
                                                className="form-control"
                                                placeholder="Type your message"
                                            />
                                            <button className="btn btn-primary">Send</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            {/* )} */}
        </>
    );
}

export default Message;
