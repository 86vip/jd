package com.xxxx.jd.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.jd.dao.ProductDao;
import com.xxxx.jd.query.ProductQuery;
import com.xxxx.jd.utils.SessionUtils;
import com.xxxx.jd.utils.StringUtils;
import com.xxxx.jd.vo.Product;
import com.xxxx.jd.vo.Role;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {
    public Map<String, Object> queryByParamsForTable(ProductQuery productQuery) {
        SqlSession sqlSession = SessionUtils.getSession();
        ProductDao productDao = sqlSession.getMapper(ProductDao.class);
        Map<String, Object> map = new HashMap<>();
        //开启分页
        PageHelper.startPage(productQuery.getPage(), productQuery.getLimit());
        //得到对应分页对象
        PageInfo<Product> pageInfo = new PageInfo<>(productDao.selectByParams(productQuery));
        sqlSession.close();
        //设置map对象
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    public Product selectByPrimaryKey(Integer id) {
        SqlSession sqlSession = SessionUtils.getSession();
        ProductDao productDao = sqlSession.getMapper(ProductDao.class);
        Product product = productDao.selectByPrimaryKey(id);
        sqlSession.close();
        return product;
    }

    /**
     * 添加产品
     *  1、参数校验
     *  2、设置默认值
     *  3、执行操作
     * @param product
     * @return
     */
    public Object addProduct(Product product) {
        SqlSession sqlSession = SessionUtils.getSession();
        ProductDao productDao = sqlSession.getMapper(ProductDao.class);
        /* 1、参数校验 */
        if (StringUtils.isBlank(product.getName())) {
            return "产品名称不能为空！";
        }
        if (StringUtils.isBlank(product.getMerchant())) {
            return "产品商家不能为空！";
        }
        if (product.getPrice() == null) {
            return "产品单价不能为空！";
        }
        if (StringUtils.isBlank(product.getType())) {
            return "产品类型不能为空！";
        }
        /* 2、设置默认值 */
        if (product.getCreateDate() == null) {
            product.setCreateDate(new Date());
        }
        product.setUpdateDate(new Date());
        product.setIsValid(1);

        /* 3、执行操作 */
        if (productDao.insertSelective(product) < 1) {
            return "产品添加失败！";
        }
        sqlSession.commit();
        sqlSession.close();
        return null;
    }

    /**
     * 更新产品
     * @param product
     * @return
     */
    public Object updateProduct(Product product) {
        SqlSession sqlSession = SessionUtils.getSession();
        ProductDao productDao = sqlSession.getMapper(ProductDao.class);
        /* 1、参数校验 */
        if (product.getId() == null || productDao.selectByPrimaryKey(product.getId()) == null) {
            return "待更新记录不存在！";
        }
        if (StringUtils.isBlank(product.getName())) {
            return "产品名称不能为空！";
        }
        if (StringUtils.isBlank(product.getMerchant())) {
            return "产品商家不能为空！";
        }
        if (product.getPrice() == null) {
            return "产品单价不能为空！";
        }
        if (StringUtils.isBlank(product.getType())) {
            return "产品类型不能为空！";
        }
        /* 2、设置默认值 */
        product.setUpdateDate(new Date());

        /* 3、执行操作 */
        if (productDao.updateByPrimaryKeySelective(product) < 1) {
            return "产品更新失败！";
        }
        sqlSession.commit();
        sqlSession.close();
        return null;
    }

    public Object deleteByIds(String[] ids) {
        SqlSession sqlSession = SessionUtils.getSession();
        ProductDao productDao = sqlSession.getMapper(ProductDao.class);
        if (ids == null || ids.length == 0) {
            return "待删除记录不存在！";
        }
        if (productDao.deleteBatch(ids) != ids.length) {
            return "删除产品失败！";
        }
        sqlSession.commit();
        sqlSession.close();
        return null;
    }

    public List<Map<String, Object>> queryAllProducts() {
        SqlSession sqlSession = SessionUtils.getSession();
        ProductDao productDao = sqlSession.getMapper(ProductDao.class);
        List<Map<String, Object>> list = productDao.queryAllProducts();
        sqlSession.close();
        return list;
    }
}
