// Layouts

// Pages
import Home from '~/pages/Home';
import Cart from '~/pages/Cart';
import Login from '~/pages/Login/index';
import Register from '~/pages/Register/index';
import Branch from '~/pages/Admin/scenes/branch';
import Calendar from '~/pages/Admin/scenes/calendar/calendar';
import Form from '~/pages/Admin/scenes/form/user';
import FormService from '~/pages/Admin/scenes/form/service';
import EditForm from '~/pages/Admin/scenes/form/editUser';
import EditFormBranch from '~/pages/Admin/scenes/form/editBranch';
import EditFormService from '~/pages/Admin/scenes/form/editService';
import Dashboard from '~/pages/Admin/scenes/dashboard/index';
import FormBranch from '~/pages/Admin/scenes/form/branch';
import BookServiceContent from '~/pages/BookServiceContent';
import Service from '~/pages/Service';
import ServiceEx from '~/pages/ServiceEx/ServiceEx';
import ServiceDetail from '~/pages/ServiceDetail';
import UserHistory from '~/pages/Admin/scenes/user-history';
import HistoryBooking from '~/pages/Admin/scenes/history';
import Staff from '~/pages/Staff';
import AccessDeny from '~/pages/Admin/Status/accessDeny';
import NotFound from '~/pages/Admin/Status/NotFound';
import MailPage from '~/pages/ForgotPass/mailPage';
import ForgotPage from '~/pages/ForgotPass/forgotPage';
import Profile from '~/pages/Profile';
import DataService from '~/pages/Admin/scenes/service';
import BookOff from '~/pages/Receptionist/BookOff';
import DataUser from '~/pages/Admin/scenes/team';
import AcceptBooking from '~/pages/Receptionist/AcceptBooking';
import Chart from '~/pages/Admin/scenes/chart';
import Contact from '~/pages/Contact';
import Event from '~/pages/Admin/scenes/form/event';
import EditFormEvent from '~/pages/Admin/scenes/form/editEvent';
import EventData from '~/pages/Admin/scenes/event/index'
import ResetPage from '~/ResetPass/ResetPage';

// Public routes
const publicRoutes = [
    { path: '/', component: Home },
    { path: '/cart', component: Cart },
    { path: '/bookservice', component: BookServiceContent },
    { path: '/login', component: Login, layout: null },
    { path: '/register', component: Register, layout: null },
    { path: '/service', component: Service },
    { path: '/serviceExample', component: ServiceEx },
    { path: '/services/:id', component: ServiceDetail },
    { path: '/staff', component: Staff },
    { path: '/branch', component: Branch, admin: true },
    { path: '/eventData', component: EventData, admin: true },
    { path: '/dataService', component: DataService, admin: true },
    { path: '/formBranch', component: FormBranch, admin: true },
    { path: '/calendar', component: Calendar, admin: true },
    { path: '/form', component: Form, admin: true },
    { path: '/dashboard', component: Dashboard, admin: true },
    { path: '/history', component: HistoryBooking, admin: true },
    { path: '/formService', component: FormService, admin: true },
    { path: '/editUser/:id', component: EditForm, admin: true },
    { path: '/editBranch/:id', component: EditFormBranch, admin: true },
    { path: '/editService/:id', component: EditFormService, admin: true },
    { path: '/accessDeny', component: AccessDeny, layout: null },
    { path: '/404', component: NotFound, layout: null },
    { path: '/profile', component: Profile },
    { path: '/user-history', component: UserHistory },
    { path: '/mail', component: MailPage, layout: null },
    { path: '/reset', component: ForgotPage, layout: null },
    { path: '/bookOff', component: BookOff },
    { path: '/dataUser', component: DataUser, admin: true },
    { path: '/chart', component: Chart, admin: true },
    { path: '/event', component: Event, admin: true },
    { path: '/editEvent/:id', component: EditFormEvent, admin: true },
    { path: '/accept', component: AcceptBooking },
    { path: '/contact', component: Contact },
    { path: '/resetpage', component:ResetPage ,layout:null },
    { path: '/*', component: NotFound, layout: null },
];


const privateRoutes = [];

export { publicRoutes, privateRoutes };
