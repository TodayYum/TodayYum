/** @type {import('tailwindcss').Config} */

export default {
  content: ["./index.html", "./src/**/*.{js,jsx,ts,tsx}"],
  mode: "jit",
  theme: {
    screens: {
      sm: { max: "393px" },
    },
    colors: {
      black: "#001F2A",
      blue: "#1fb6ff",
      purple: "#7e5bef",
      pink: "#ff49db",
      orange: "#ff7849",
      green: "#13ce66",
      yellow: "#ffc82c",
      "gray-dark": "#787D85",
      gray: "#B2B0B0",
      "gray-light": "#d3dce6",
      white: "#ffffff",
    },
    fontFamily: {
      ggTitle: ["GGTitle", "sans-serif"],
    },
    extend: {
      colors: {
        primary: "#ED5D5D",
        "primary-container": "#FF8D8D",
        secondary: "#FFAA46",
        "secondary-container": "#FFD8AA",
        error: "#ED3A50",
        background: "#F8F8F8",
        correct: "#4966FF",
        thirdly: "#31E579",
      },
      spacing: {
        "8xl": "96rem",
        "9xl": "128rem",
      },
      padding: {
        normal: "15px",
      },
      borderRadius: {
        small: "4px",
        large: "16px",
      },
    },
  },
};
