package com.example.zzn.nuomi.model;

import java.io.Serializable;

/**
 * Created by ZZN on 2017/8/22.
 */
//{"status":"0","msg":"ok","result":
// {"city":"绍兴","cityid":"388","citycode":"101210501","date":"2017-08-23",
// "week":"星期三","weather":"多云","temp":"34",
// "temphigh":"36","templow":"29","img":"1",
// "humidity":"59","pressure":"1007",
// "windspeed":"11.0","winddirect":"东南风","windpower":
// "2级","updatetime":"2017-08-23 13:35:04",
// "index":[{"iname":"空调指数","ivalue":"部分时间开启",
// "detail":"您将感到些燥热，建议您在适当的时候开启制冷空调来降低温度，以免中暑。"},
// {"iname":"运动指数","ivalue":"较不宜","detail":"天气较好，但风力较强，在户外要选择合适的运动，另外考虑到天气炎热，建议停止高强度运动。"},
// {"iname":"紫���线指数","ivalue":"中等","detail":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"},
// {"iname":"感冒指数","ivalue":"少发","detail":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。"},
// {"iname":"洗车指数","ivalue":"较适宜","detail":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},
// {"iname":"空气污染扩散指数","ivalue":"良","detail":"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。"},{"iname":"穿衣指数","ivalue":"炎热","detail":"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。"}],"aqi":{"so2":"6","so224":"6","no2":"4","no224":"9","co":"0.470","co24":"0.570","o3":"67","o38":"40","o324":"40","pm10":"19","pm1024":"18","pm2_5":"10","pm2_524":"9","iso2":"3","ino2":"3","ico":"5","io3":"22","io38":"20","ipm10":"19","ipm2_5":"15","aqi":"22","primarypollutant":"O3","quality":"优","timepoint":"2017-08-23 13:00:00","aqiinfo":{"level":"一级","color":"#00e400","affect":"空气质量令人满意，基本无空气污染","measure":"各类人群可正常活动"}},"daily":[{"date":"2017-08-23","week":"星期三","sunrise":"05:29","sunset":"18:30","night":{"weather":"晴","templow":"29","img":"0","winddirect":"无持续风向","windpower":"微风"},"day":{"weather":"多云","temphigh":"36","img":"1","winddirect":"东南风","windpower":"3-4 级"}},{"date":"2017-08-24","week":"星期四","sunrise":"05:30","sunset":"18:29","night":{"weather":"晴","templow":"29","img":"0","winddirect":"无持续风向","windpower":"微风"},"day":{"weather":"晴","temphigh":"37","img":"0","winddirect":"无持续风向","windpower":"微风"}},{"date":"2017-08-25","week":"星期五","sunrise":"05:30","sunset":"18:28","night":{"weather":"多云","templow":"29","img":"1","winddirect":"无持续风向","windpower":"微风"},"day":{"weather":"雷阵雨","temphigh":"36","img":"4","winddirect":"无持续风向","windpower":"微风"}},{"date":"2017-08-26","week":"星期六","sunrise":"05:31","sunset":"18:27","night":{"weather":"晴","templow":"26","img":"0","winddirect":"无持续风向","windpower":"微风"},"day":{"weather":"雷阵雨","temphigh":"34","img":"4","winddirect":"无持续风向","windpower":"微风"}},{"date":"2017-08-27","week":"星期日","sunrise":"05:31","sunset":"18:26","night":{"weather":"晴","templow":"25","img":"0","winddirect":"无持续风向","windpower":"微风"},"day":{"weather":"晴","temphigh":"36","img":"0","winddirect":"无持续风向","windpower":"微风"}},{"date":"2017-08-28","week":"星期一","sunrise":"07:30","sunset":"19:30","night":{"weather":"多云","templow":"27","img":"1","winddirect":"东南风","windpower":"微风"},"day":{"weather":"多云","temphigh":"39","img":"1","winddirect":"东南风","windpower":"微风"}},{"date":"2017-08-29","week":"星期二","sunrise":"07:30","sunset":"19:30","night":{"weather":"多云","templow":"27","img":"1","winddirect":"","windpower":"微风"},"day":{"weather":"晴","temphigh":"38","img":"0","winddirect":"","windpower":"微风"}}],"hourly":[{"time":"14:00","weather":"雷阵雨","temp":"34","img":"4"},{"time":"15:00","weather":"晴","temp":"34","img":"0"},
public class WeatherResult {
    private Result result;
    private String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public Result getResult() {
        return result;
    }

    public void setResults(Result results) {
        this.result = result;
    }

    public class Result implements Serializable{
        public String city;
        private String date;
        private String week;
        private String weather;
        private String temp;
        private String winddirect;
        private String windpower;
        private String humidity;
        private String img;
        private Aqi aqi;

        public Aqi getAqi() {
            return aqi;
        }

        public void setAqi(Aqi aqi) {
            this.aqi = aqi;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getWinddirect() {
            return winddirect;
        }

        public void setWinddirect(String winddirect) {
            this.winddirect = winddirect;
        }

        public String getWindpower() {
            return windpower;
        }

        public void setWindpower(String windpower) {
            this.windpower = windpower;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public class Aqi {
            private String aqi;
            private String quality;

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String api) {
                this.aqi = aqi;
            }

            public String getQuality() {
                return quality;
            }

            public void setQuality(String quality) {
                this.quality = quality;
            }
        }
    }
}
