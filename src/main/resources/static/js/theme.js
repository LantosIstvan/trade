"use strict";

// Eseményfigyelőt ad hozzá az ablak átméretezéséhez.
const resize = (callback) => window.addEventListener("resize", callback);

// Egy stringet camelCase formátumúvá alakít (pl. "hello-world" -> "helloWorld").
const camelize = (str) => {
    const text = str.replace(/[-_\s.]+(.)?/g, (match, c) => c ? c.toUpperCase() : "");
    return `${text.charAt(0).toLowerCase()}${text.slice(1)}`;
};

// Kiolvas egy data-* attribútumot egy HTML elemről, és megpróbálja JSON-ként értelmezni.
const getData = (element, key) => {
    try {
        return JSON.parse(element.dataset[camelize(key)]);
    } catch (e) {
        return element.dataset[camelize(key)];
    }
};

// Hexadecimális színkódot RGB tömbbé alakít.
const hexToRgb = (hex) => {
    let cleanHex = hex.startsWith("#") ? hex.substring(1) : hex;
    // Rövidített hex kód (pl. #F00) kiegészítése (#FF0000)
    if (cleanHex.length === 3) {
        cleanHex = cleanHex.split('').map(char => char + char).join('');
    }
    const result = /^([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(cleanHex);
    return result ? [parseInt(result[1], 16), parseInt(result[2], 16), parseInt(result[3], 16)] : null;
};

// Szükséges színek és töréspontok
const colors = {
    primary: "#0057FF",
    secondary: "#748194",
    success: "#00d27a",
    info: "#27bcfd",
    warning: "#f5803e",
    danger: "#e63757",
    light: "#f9fafd", // Ez a világos szín lesz az alapértelmezett
    dark: "#000",
};
const grays = {
    300: "#d8e2ef"
};
const breakpoints = {
    xs: 0,
    sm: 576,
    md: 768,
    lg: 992,
    xl: 1200,
    xxl: 1540
};

// Meghatározza egy Bootstrap navbar töréspontját az osztályai alapján.
const getBreakpoint = (navbar) => {
    const expandClass = [...navbar.classList].find(cls => cls.startsWith("navbar-expand-"));
    if (expandClass) {
        const breakpointName = expandClass.split("-").pop();
        return breakpoints[breakpointName];
    }
    return undefined;
};


// =======================================================================================
// Fő funkciók
// =======================================================================================

// Dinamikus navbar, ami görgetésre megváltoztatja a kinézetét.
const navbarDarkenOnScroll = () => {
    const navbar = document.querySelector("[data-navbar-darken-on-scroll]");
    if (!navbar) return;

    const windowHeight = window.innerHeight;
    const navbarCollapse = navbar.querySelector(".navbar-collapse");
    const navbarToggler = navbar.querySelector(".navbar-toggler");

    const allColors = { ...colors, ...grays };
    let colorName = getData(navbar, "navbar-darken-on-scroll");

    // JAVÍTVA: Az alapértelmezett szín "dark" helyett "light".
    colorName = allColors[colorName] ? colorName : "light";

    const colorRgb = hexToRgb(allColors[colorName]);
    const borderColorRgb = hexToRgb(grays[300]);
    const originalBgImage = window.getComputedStyle(navbar).backgroundImage;

    // Görgetés eseményfigyelő
    window.addEventListener("scroll", () => {
        const alpha = Math.min(1, (window.scrollY / windowHeight) * 2);
        const isNavbarOpen = navbarCollapse.classList.contains("show");

        navbar.style.backgroundColor = `rgba(${colorRgb.join(', ')}, ${alpha})`;
        navbar.style.borderBottom = `1px solid rgba(${borderColorRgb.join(', ')}, ${alpha})`;
        navbar.style.backgroundImage = alpha > 0 || isNavbarOpen ? originalBgImage : "none";

        if (alpha > 0.2 || isNavbarOpen) {
            navbar.classList.add("shadow-transition");
        } else {
            navbar.classList.remove("shadow-transition");
        }
    });

    // Átméretezéskor a mobil/desktop nézet kezelése
    resize(() => {
        const breakpoint = getBreakpoint(navbar);
        if (window.innerWidth > breakpoint) {
            navbar.style.backgroundImage = window.scrollY ? originalBgImage : "none";
            navbar.style.transition = "none";
        } else if (!navbarToggler.classList.contains("collapsed")) {
            navbar.style.backgroundImage = originalBgImage;
        }
    });

    // Bootstrap collapse események a mobil menü nyitásakor/zárásakor
    navbarCollapse.addEventListener("show.bs.collapse", () => {
        navbar.style.backgroundImage = originalBgImage;
    });
    navbarCollapse.addEventListener("hide.bs.collapse", () => {
        if (!window.scrollY) {
            navbar.style.backgroundImage = "none";
        }
    });
};

// Rekurzívan összefésül több objektumot.
function deepMerge(target, ...sources) {
    for (const source of sources) {
        for (const key in source) {
            if (source[key] instanceof Object && key in target) {
                Object.assign(source[key], deepMerge(target[key], source[key]));
            }
        }
    }
    Object.assign(target || {}, ...sources);
    return target;
}

// Inicializálja a Swiper.js slidereket az oldalon.
const swiperInit = () => {
    document.querySelectorAll(".swiper-container").forEach(element => {
        const userOptions = JSON.parse(element.dataset.option || '{}');

        const defaultOptions = {
            slidesPerView: 1,
            spaceBetween: 30,
            pagination: {
                el: document.getElementById(element.dataset.paginationTarget),
                type: "bullets",
                clickable: true,
            },
            breakpoints: {
                670: { slidesPerView: 2, spaceBetween: 20 },
                1200: { slidesPerView: 3, spaceBetween: 50 },
            },
        };

        new window.Swiper(element, deepMerge(defaultOptions, userOptions));
    });
};

// =======================================================================================
// Inicializálás
// =======================================================================================

window.addEventListener('DOMContentLoaded', () => {
    swiperInit();
    navbarDarkenOnScroll();
});