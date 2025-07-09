class SplashCursor {
    constructor() {
        this.cursor = null;
        this.splashes = [];
        this.isMoving = false;
        this.lastTime = 0;
        this.init();
    }

    init() {
        // Tạo cursor element
        this.cursor = document.createElement('div');
        this.cursor.className = 'splash-cursor';
        document.body.appendChild(this.cursor);

        // Bind events
        document.addEventListener('mousemove', this.handleMouseMove.bind(this));
        document.addEventListener('click', this.handleClick.bind(this));
        document.addEventListener('mouseenter', this.handleMouseEnter.bind(this));
        document.addEventListener('mouseleave', this.handleMouseLeave.bind(this));
    }

    handleMouseMove(e) {
        const currentTime = Date.now();

        // Throttle để tối ưu performance
        if (currentTime - this.lastTime < 16) return;
        this.lastTime = currentTime;

        // Cập nhật vị trí cursor
        this.cursor.style.left = e.clientX + 'px';
        this.cursor.style.top = e.clientY + 'px';

        // Tạo splash khi di chuyển nhanh
        if (!this.isMoving) {
            this.isMoving = true;
            setTimeout(() => {
                this.isMoving = false;
            }, 100);
        }
    }

    handleClick(e) {
        this.createSplash(e.clientX, e.clientY, 'click');

        // Hiệu ứng đặc biệt cho form elements
        if (e.target.matches('input, button, select, textarea')) {
            this.createFormSplash(e.clientX, e.clientY);
        }
    }

    handleMouseEnter() {
        this.cursor.style.opacity = '1';
    }

    handleMouseLeave() {
        this.cursor.style.opacity = '0';
    }

    createSplash(x, y, type = 'default') {
        const splash = document.createElement('div');
        splash.className = 'splash';

        // Tùy chỉnh theo loại splash
        switch(type) {
            case 'click':
                splash.style.background = 'radial-gradient(circle, rgba(74,144,226,0.8) 0%, rgba(74,144,226,0.4) 50%, transparent 70%)';
                break;
            case 'form':
                splash.style.background = 'radial-gradient(circle, rgba(52,211,153,0.8) 0%, rgba(52,211,153,0.4) 50%, transparent 70%)';
                break;
            default:
                splash.style.background = 'radial-gradient(circle, rgba(255,255,255,0.8) 0%, rgba(255,255,255,0.4) 50%, transparent 70%)';
        }

        splash.style.left = x + 'px';
        splash.style.top = y + 'px';

        document.body.appendChild(splash);
        this.splashes.push(splash);

        // Xóa splash sau khi animation hoàn thành
        setTimeout(() => {
            if (splash.parentNode) {
                splash.parentNode.removeChild(splash);
            }
            this.splashes = this.splashes.filter(s => s !== splash);
        }, 600);
    }

    createFormSplash(x, y) {
        // Tạo nhiều splash nhỏ xung quanh
        for (let i = 0; i < 3; i++) {
            setTimeout(() => {
                const offsetX = (Math.random() - 0.5) * 40;
                const offsetY = (Math.random() - 0.5) * 40;
                this.createSplash(x + offsetX, y + offsetY, 'form');
            }, i * 100);
        }
    }

    destroy() {
        // Cleanup
        if (this.cursor && this.cursor.parentNode) {
            this.cursor.parentNode.removeChild(this.cursor);
        }

        this.splashes.forEach(splash => {
            if (splash.parentNode) {
                splash.parentNode.removeChild(splash);
            }
        });

        document.removeEventListener('mousemove', this.handleMouseMove);
        document.removeEventListener('click', this.handleClick);
        document.removeEventListener('mouseenter', this.handleMouseEnter);
        document.removeEventListener('mouseleave', this.handleMouseLeave);
    }
}

// Khởi tạo khi DOM ready
document.addEventListener('DOMContentLoaded', function() {
    const splashCursor = new SplashCursor();

    // Cleanup khi trang bị unload
    window.addEventListener('beforeunload', function() {
        splashCursor.destroy();
    });
});