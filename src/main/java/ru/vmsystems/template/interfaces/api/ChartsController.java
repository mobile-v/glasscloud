package ru.vmsystems.template.interfaces.api;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vmsystems.template.domain.model.tag.TagsEntity;
import ru.vmsystems.template.infrastructure.persistence.TagRepository;
import ru.vmsystems.template.infrastructure.persistence.TagsRepository;
import ru.vmsystems.template.interfaces.dto.ChartData;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/charts")
public final class ChartsController {
    @NotNull
    private static final Logger LOG = LoggerFactory.getLogger(ChartsController.class);
//    @NotNull
//    @Autowired
//    private HttpServletRequest httpServletRequest;

    @NotNull
    @Autowired
    private TagsRepository tagsRepository;

    @NotNull
    @Autowired
    private TagRepository tagRepository;

    //http://localhost:8080/charts/data/1
    @NotNull
    @RequestMapping(value = "/data/{autoclaveId}", method = RequestMethod.GET)
    public ResponseEntity<List<ChartData>> getProducts(
            @NotNull @PathVariable Integer autoclaveId,
            @RequestParam(defaultValue = "4000", required = false) Integer count,
            @RequestParam(defaultValue = "0", required = false) Integer page
            ) {

        if (count < 1) count = 1;
        System.out.println(page);
        List<ChartData> result = new ArrayList<>();

        Map<Timestamp, ChartData> map = new TreeMap<>();

        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        Comparator<TagsEntity> byDate = Comparator.comparing(TagsEntity::getDate);

        tagsRepository.getTags(autoclaveId, new PageRequest(page, count))
                .stream().sorted(byDate)
                .forEach(tag -> {

                    ChartData data = map.get(tag.getDate());
                    if (data == null) {
                        data = new ChartData();
                        data.setDate(formatter.format(tag.getDate()));
                    }

                    getValue(tag, data);

                    map.put(tag.getDate(), data);
                });

        map.forEach((timestamp, chartData) -> result.add(chartData));

        System.out.println("datas = " + result.size() + " ");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    //http://localhost:8080/charts/data/online/1
    @NotNull
    @RequestMapping(value = "/data/online/{autoclaveId}", method = RequestMethod.GET)
    public ResponseEntity<ChartData> onlineData(
            @NotNull @PathVariable Integer autoclaveId) {

        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        ChartData data = new ChartData();

        tagRepository.findByIdCpuAndType(autoclaveId, "r")
                .forEach(tagEntity -> {
                    TagsEntity tag = tagsRepository.findTop1ByTagNameAndAutoclaveOrderByIdDesc(tagEntity.getName(), tagEntity.getIdCpu());
                    data.setDate(formatter.format(tag.getDate()));

                    getValue(tag, data);
                });

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    private void getValue(TagsEntity tag, ChartData data) {
        switch (tag.getTag().getName()) {
            case "T1":
                data.setT1(String.format("%.0f", tag.getValue()).replace(",", "."));
                break;
            case "P1":
                data.setP1(String.format("%.2f", tag.getValue()).replace(",", "."));
                break;
            case "P3":
                data.setP3(String.format("%.2f", tag.getValue()).replace(",", "."));
                break;
        }
    }
}


//    //http://localhost:8082/api/wall/movie/movie1
//    @NotNull
//    @RequestMapping(value = "/movie/{name}", method = RequestMethod.GET, produces = "video/mp4")
//    public ResponseEntity<byte[]> getMovie(@NotNull @PathVariable String name) {
//
//        Optional<FileEntity> fileDb = filesDao.getByName(name);
//        if (fileDb.isPresent()) {
//            byte[] file = filesDao.getFile(fileDb.get().getFileName());
//
//            if (file != null) {
//                LOG.info("\nСкачивание видео: {} {}.{} с ip: {} девайсом: {}. Размер файла {}\n",
//                        fileDb.get().getFileName(), fileDb.get().getName(), fileDb.get().getExt(),
//                        httpServletRequest.getRemoteHost(), httpServletRequest.getRemoteUser(),
//                        file.length);
//
//                return new ResponseEntity<>(file, HttpStatus.OK);
//            }
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    //http://localhost:8082/api/wall/image/image1
//    @NotNull
//    @RequestMapping(value = "/image/{name}", method = RequestMethod.GET, produces = "image/jpeg")
//    public ResponseEntity<byte[]> getImage(@NotNull @PathVariable String name) {
//
//        Optional<FileEntity> fileDb = filesDao.getByName(name);
//        if (fileDb.isPresent()) {
//            byte[] file = filesDao.getFile(fileDb.get().getFileName());
//
//            if (file != null) {
//                LOG.info("\nСкачивание картинки: {} {}.{} с ip: {} девайсом: {}. Размер файла {}\n",
//                        fileDb.get().getFileName(), fileDb.get().getName(), fileDb.get().getExt(),
//                        httpServletRequest.getRemoteHost(), httpServletRequest.getRemoteUser(),
//                        file.length);
//
//                return new ResponseEntity<>(file, HttpStatus.OK);
//            }
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//}
