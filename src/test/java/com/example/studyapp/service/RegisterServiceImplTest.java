package com.example.studyapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentCaptor;

import com.example.studyapp.dao.RegisterDao;
import com.example.studyapp.entity.UserEntity;
import com.example.studyapp.entity.UserInfo;
import com.example.studyapp.entity.UserInfoExample;
import com.example.studyapp.form.RegisterForm;
import com.example.studyapp.mapper.UserInfoMapper;

@ExtendWith(MockitoExtension.class)
class RegisterServiceImplTest {

    @Mock
    private RegisterDao registerDao;

    @Mock
    private UserInfoMapper userInfoMapper;

    @InjectMocks
    private RegisterServiceImpl registerService;

    private RegisterForm registerForm;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        registerForm = new RegisterForm();
        registerForm.setUserName("testUser");
        registerForm.setUserPassword("password");

        userEntity = new UserEntity();
        userEntity.setUserId(1);
        userEntity.setUserName("testUser");
        userEntity.setUserPassword("password");
    }

    @Nested
    class CheckUserNameTest {
        ArgumentCaptor<UserInfoExample> captor = ArgumentCaptor.forClass(UserInfoExample.class);
        @Test
        void 既存ユーザーの場合_trueを返す() {
            // 期待値の条件をMapで定義
            Map<String, String> expectedCriteria = Map.of(
                "condition", "user_name =",
                "value", "testUser"
            );

            // 準備
            String userName = "testUser";
            when(userInfoMapper.countByExample(any(UserInfoExample.class))).thenReturn(1L);

            // 実行
            boolean result = registerService.checkUserName(userName);

            // 検証
            assertThat(result).isTrue();
            verify(userInfoMapper, times(1)).countByExample(captor.capture());
            // Mapを使用した検証
            UserInfoExample capturedExample = captor.getValue();
            assertThat(capturedExample.getOredCriteria().get(0).getCriteria().get(0))
                .satisfies(criteria -> {
                    assertThat(criteria.getCondition()).isEqualTo(expectedCriteria.get("condition"));
                    assertThat(criteria.getValue()).isEqualTo(expectedCriteria.get("value"));
            });
        }

        @Test
        void 新規ユーザーの場合_falseを返す() {
            // 期待値の条件をMapで定義
            Map<String, String> expectedCriteria = Map.of(
                "condition", "user_name =",
                "value", "newUser"
            );

            // 準備
            String userName = "newUser";
            when(userInfoMapper.countByExample(any(UserInfoExample.class))).thenReturn(0L);

            // 実行
            boolean result = registerService.checkUserName(userName);

            // 検証
            assertThat(result).isFalse();
            verify(userInfoMapper, times(1)).countByExample(captor.capture());
            // Mapを使用した検証
            UserInfoExample capturedExample = captor.getValue();
            assertThat(capturedExample.getOredCriteria().get(0).getCriteria().get(0))
                .satisfies(criteria -> {
                    assertThat(criteria.getCondition()).isEqualTo(expectedCriteria.get("condition"));
                    assertThat(criteria.getValue()).isEqualTo(expectedCriteria.get("value"));
            });
        }
    }

    @Nested
    class RegistUserInfoTest {
        @Test
        void 正常に登録できる() {
            // 準備
            ArgumentCaptor<UserInfo> captor = ArgumentCaptor.forClass(UserInfo.class);
            when(userInfoMapper.insert(any(UserInfo.class))).thenReturn(1); 

            // 実行
            registerService.registUserInfo(registerForm);

            // 検証
            verify(userInfoMapper, times(1)).insert(captor.capture());  // 1回呼び出されることを検証
            UserInfo capturedUserInfo = captor.getValue();
            
            // UserInfoの各フィールドを検証
            assertThat(capturedUserInfo.getUserName()).isEqualTo(registerForm.getUserName());
            assertThat(capturedUserInfo.getUserPassword()).isEqualTo(registerForm.getUserPassword());
        }
    }

    @Nested
    class FindByUserNameTest {
        @Test
        void ユーザーが存在する場合_ユーザー情報を返す() {
            // 準備
            String userName = "testUser";
            when(registerDao.findByUserName(userName)).thenReturn(userEntity);

            // 実行
            UserEntity result = registerService.findByUserName(userName);

            // 検証
            assertThat(result).isNotNull();
            assertThat(result.getUserName()).isEqualTo(userName);
            verify(registerDao, times(1)).findByUserName(userName);
        }

        @Test
        void ユーザーが存在しない場合_nullを返す() {
            // 準備
            String userName = "nonexistentUser";
            when(registerDao.findByUserName(userName)).thenReturn(null);

            // 実行
            UserEntity result = registerService.findByUserName(userName);

            // 検証
            assertThat(result).isNull();
            verify(registerDao, times(1)).findByUserName(userName);
        }
    }
}