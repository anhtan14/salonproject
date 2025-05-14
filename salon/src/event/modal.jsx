import React, { useEffect, useState } from 'react';
import Modal from 'react-modal';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import './modal.scss'
import axios from '~/utils/api/axios';

Modal.setAppElement('#root');



function MyModal() {
    const [open, setOpen] = useState(true);
    const [event ,setEvent] =useState({})
    const [d ,setD] =useState(null)
  
    console.log(event)
    if( d==null){
        console.log(1)
        axios.post('/booking/event')
        .then((res)=>{
            setEvent(res.data)
            console.log(res.data)
            setD(1)
           if(res.data=='not'){
            setOpen(false)
           }
        })
}
        
  const style = {
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: '100%',
    height:'100%',
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
  };
  
  const customStyles = {
    content: {
      top: '50%', // Đặt vị trí theo chiều dọc
      left: '50%', // Đặt vị trí theo chiều ngang
      transform: 'translate(-50%, -50%)', // Dịch chuyển để căn giữa modal
      width: '80%',
      maxWidth: '400px',
      maxheight:'500px',
      border: '1px solid #ccc',
      borderRadius: '4px',
      boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
    }
}
  const handleClose = () => setOpen(false);
  

  return (
    <Modal
  isOpen={open}
  onRequestClose={handleClose}
  style={customStyles}
>
  <Box className='modal-container' sx={style} >
   <img className='anh' src={event.img} alt="" />
  </Box>
</Modal>
  );
}

export default MyModal;
