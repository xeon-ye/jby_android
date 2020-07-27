package com.jkrm.education.bean;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/14 15:48
 */

public class ClassessBean {


    /**
     * code : 200
     * data : {"2018级":[{"name":"2018级10班","id":"1a559e4326557ae9b661b87c0a76ff04"},{"name":"2018级11班","id":"d253b8a4909627729a60c8cab9a3e793"},{"name":"2018级12班","id":"c64484a73b45f7e150e4aff13f551af2"},{"name":"2018级13班","id":"59bee7a00d13960c33c42714ec5d7920"},{"name":"2018级14班","id":"707e329787b652b11af1452e7fd8d6d4"},{"name":"2018级15班","id":"2fb762cfd808e0d790bc79897a249acd"},{"name":"2018级16班","id":"a7f037549789f0eb930115a016d727bb"},{"name":"2018级17班","id":"bddbcaff8c6b2d4f78d16115a6dfc50a"},{"name":"2018级18班","id":"2b253d600208c1848e5a514fbda02bb1"},{"name":"2018级19班","id":"736f474814dfad159f2347dd4cd0dda5"},{"name":"2018级1班","id":"00d4c9ed48af0cac3cf754a1dd8cfb32"},{"name":"2018级20班","id":"901c52ced3c3f239b0a061319635e4ea"},{"name":"2018级21班","id":"192536121a228223c5924ea50bd3a13c"},{"name":"2018级22班","id":"934c94ab4f67c9a1233bdf6184494a9a"},{"name":"2018级23班","id":"d12171e7770fbd7177cfb7da3d72647b"},{"name":"2018级24班","id":"43833c4a1c90e756dcdede25eac301f0"},{"name":"2018级25班","id":"27267844799257ae63beadebfc91d01b"},{"name":"2018级26班","id":"56e42cea10e5e5306d2586a2843e7183"},{"name":"2018级27班","id":"72924c809cafd229ee82cb3dd019fef1"},{"name":"2018级28班","id":"b17786ec0daef0aeb8084735da127c86"},{"name":"2018级29班","id":"4ca705088fbd9a67242198de27bcbf99"},{"name":"2018级2班","id":"bb79fe25b71af2c69b4827a0ad1fbfa0"},{"name":"2018级30班","id":"01540ffdc338e63ddd45bffef1a840c6"},{"name":"2018级31班","id":"83a379d81a262d04ed5416c2b0503293"},{"name":"2018级32班","id":"0d925667839d869fd1877ed8b6089a47"},{"name":"2018级33班","id":"1a3cf2d93f22fc60d21be9e81e816a82"},{"name":"2018级34班","id":"fc8fe48196210cb669a90e3592cae7ae"},{"name":"2018级35班","id":"3ac79d87adc79e58f5486e5c4177b2c2"},{"name":"2018级36班","id":"7c7adff18e68ed219f1b1315c0553a92"},{"name":"2018级37班","id":"9029f0b902c2d119675e42e443b8f901"},{"name":"2018级38班","id":"a9de4660b0ce758e20f4d3f47880321d"},{"name":"2018级39班","id":"8633962a2a69bc9d8f9ffdf2833bda00"},{"name":"2018级3班","id":"19557992dae8beb854f30641d150f75a"},{"name":"2018级40班","id":"950372deca4711374a418b39b5f60eb8"},{"name":"2018级41班","id":"2ce9b06df3c01a32cb2372ae0513c5d9"},{"name":"2018级42班","id":"688c5f6bdbe6d63daa6c4fd264e509c7"},{"name":"2018级43班","id":"1dc1abff2d405607989e46b0ad728d00"},{"name":"2018级44班","id":"4b0d0548aaf47427d454c66a7b65cde1"},{"name":"2018级45班","id":"8e237ece0f7a750e3cb452af8a7a6b43"},{"name":"2018级46班","id":"aff88988ea505e018ef59e673b394753"},{"name":"2018级47班","id":"d5d2a91efaaac68ad1d5a4da8d27bfb4"},{"name":"2018级48班","id":"45764cecb8c927f5f851b0f66969c872"},{"name":"2018级49班","id":"9ccc3de88c2b4d588bd75d3daa4a51f3"},{"name":"2018级4班","id":"de6f410012c07dafee850b57ce513245"},{"name":"2018级50班","id":"de578778b46fa7b02e0cfb754ac1c230"},{"name":"2018级5班","id":"ee39507f0344768b71b6999366f9da95"},{"name":"2018级6班","id":"d7dabd936bf3ff089e42117255a1d300"},{"name":"2018级7班","id":"1f2e0daa88b5441a82ce0763d0662c75"},{"name":"2018级8班","id":"6a1b01ccc09e70518fe8cf55c7860600"},{"name":"2018级9班","id":"169f33c7114a691b5a575d382b9af64d"}],"2019级":[{"name":"2019级10班","id":"6190b6678302d0312e9258a66b4a189e"},{"name":"2019级11班","id":"d421879c8265ae3c93782a842335423f"},{"name":"2019级12班","id":"84ff831b049df6f3fbe86506921e26af"},{"name":"2019级13班","id":"cb881b0ce3ffb6e015555abb315f4a5d"},{"name":"2019级14班","id":"9522d3b5f2574a3fe2c1b995597b3a24"},{"name":"2019级15班","id":"0ecd313bbc310f15f1246c3eb4294b53"},{"name":"2019级16班","id":"729672ea9c63c4510737721165811190"},{"name":"2019级17班","id":"ae9ece3d4ad6d358961f15396c7fc142"},{"name":"2019级18班","id":"734a4f103ed58a4e16093c0ef38c8678"},{"name":"2019级19班","id":"78ef4d43b31e7aa3fcf9d631b310bcc0"},{"name":"2019级1班","id":"274797a237f2ac50f9b6c58b922a20be"},{"name":"2019级20班","id":"01c42b65a3f9958831a09f7995ecebc0"},{"name":"2019级21班","id":"82bd4cd2aef74035e05cc5eed43cec5f"},{"name":"2019级22班","id":"1cbe485bcca88912f0a46c5c7371d92b"},{"name":"2019级23班","id":"94f28abd301f427710c75d316a8f0fed"},{"name":"2019级24班","id":"64729048506ea528f54b7f63c95663f2"},{"name":"2019级25班","id":"3f95b24e80ce373fb82c57c027aa618b"},{"name":"2019级26班","id":"d1d5fab7611c48c21986d4705120c62c"},{"name":"2019级27班","id":"d781071fe4b8cdc70cd2c58dadb22fee"},{"name":"2019级28班","id":"86c59572b1ff0542ad7e2fc5a0f572cb"},{"name":"2019级29班","id":"acee04d494937a21a2113dfd5f433092"},{"name":"2019级2班","id":"028c3ac44a9c3a5232510235d4c15ed0"},{"name":"2019级30班","id":"9e1225ad50ec5bc1f53cf3bbf621e72c"},{"name":"2019级31班","id":"c2ae38520e58e959ea2aae93678bbb6d"},{"name":"2019级32班","id":"eb251d69062d9f2ec90e97e34ceb5eb2"},{"name":"2019级33班","id":"490ae17b4b2bd8d432f475be7ad6396c"},{"name":"2019级34班","id":"44341702004d50f78826cec6616230bb"},{"name":"2019级35班","id":"31a95f6d3dd60ab6ff8d13f6b1adf373"},{"name":"2019级36班","id":"65b4b9aeabf705acb97e18143c168359"},{"name":"2019级37班","id":"6d0be5116d5235f48cf42784039485d9"},{"name":"2019级38班","id":"d5e50b06d1f6bfec624b2c1fde82269a"},{"name":"2019级39班","id":"bf72ea62b71354be6a1aa213cb97bf6a"},{"name":"2019级3班","id":"8687e9acf3f43a708311bca1df10d97f"},{"name":"2019级40班","id":"57acf83719fd4b6067e18fe093653390"},{"name":"2019级41班","id":"cd666953e197e3438b983b76cfdae608"},{"name":"2019级42班","id":"4a566c90ea0332f6ff581a7e8d889557"},{"name":"2019级43班","id":"ce659c030b589016a5fb67045358b372"},{"name":"2019级44班","id":"a85bc2f14d872d199467a9a09b8a595e"},{"name":"2019级45班","id":"f6055255769064955c6478f6ca92a11b"},{"name":"2019级46班","id":"4e797a6f3ad03ccd9ddc8ae96394aa42"},{"name":"2019级47班","id":"8868b738c3a7c4d04baf17ed1cdd2b34"},{"name":"2019级48班","id":"367e1f185fc477635fd527089349fab9"},{"name":"2019级49班","id":"1d8faa4fe4aa1c0d70a0004cdd959745"},{"name":"2019级4班","id":"e4cf8c1a08491c0aaaeaea0f862941c1"},{"name":"2019级50班","id":"efa8a177a3d8480a5045dd0d1f4e3de9"},{"name":"2019级5班","id":"38931b706518bd71165d9bc62d38fb07"},{"name":"2019级6班","id":"4d45e872532bedb2578c76d6d8f6a011"},{"name":"2019级7班","id":"6a7adb5a1010c9d30b5770939700b56a"},{"name":"2019级8班","id":"d79367a970a8334d54254a95bcad37ee"},{"name":"2019级9班","id":"e289bf4b1f2dc3899e38d914baad2297"}],"2020级":[{"name":"2020级10班","id":"67388037577505a77c19093311f28d58"},{"name":"2020级11班","id":"c8ba166dc57026accb300368a5e3303d"},{"name":"2020级12班","id":"6a7d0df81f2bc7f813a8b98c557ed0f5"},{"name":"2020级13班","id":"4bd177ef4946fcff9a4b08ac8a5b09f7"},{"name":"2020级14班","id":"facf207eb074fe27e5e02ebc98b719a3"},{"name":"2020级15班","id":"7bfb6df60c54a44f7bc969f85c3638c8"},{"name":"2020级16班","id":"bbfb4000d4fe8e1d45075ed0103bb58a"},{"name":"2020级17班","id":"38f826674f7b2827f22f899c6a25f218"},{"name":"2020级18班","id":"0b402694488dd78119d26beb975a721f"},{"name":"2020级19班","id":"e86822a01e263267c01ac822fa5a35a6"},{"name":"2020级1班","id":"2b5a4c2ebec024575cc68f9c0e5c1cad"},{"name":"2020级20班","id":"194c830278c52d8c1885b29f60a839ff"},{"name":"2020级21班","id":"4ce3b1d5adf35d9e0cd5eb389b419473"},{"name":"2020级22班","id":"62271869ac047338976577c8d6a0d165"},{"name":"2020级23班","id":"126537e40777b432a6fb17632736b36e"},{"name":"2020级24班","id":"c438d8270630d3f317c0bb9a87edf0b5"},{"name":"2020级25班","id":"eb1b0033896020adb13054f997adb880"},{"name":"2020级26班","id":"26a77b81eabfa3b9ead450812c2b3ec9"},{"name":"2020级27班","id":"19fcddd8aa1634fcc09712d4733960ae"},{"name":"2020级28班","id":"0d1798442fc9625b4a439ccc5d890fd6"},{"name":"2020级29班","id":"bd33408459787866c4ae479ccaeb7ce0"},{"name":"2020级2班","id":"7f2f731bfd11a017048c4fa6fb459c1b"},{"name":"2020级30班","id":"c512c5e1cfa54e0ef761cd6257421f69"},{"name":"2020级31班","id":"3f30fbbf6304274d2ae8f091f9b4fbb9"},{"name":"2020级32班","id":"23247e846531e53a9aa580c24d6607ab"},{"name":"2020级33班","id":"709662d18373bbecefbceaa572b73ae6"},{"name":"2020级34班","id":"6c7887143ef341e9fc415b6229c6267f"},{"name":"2020级35班","id":"9745b8b314e9c5bc1c043c5f1ee3b231"},{"name":"2020级36班","id":"ff93d747ea096c2caa8c9088e6bf138a"},{"name":"2020级37班","id":"ff6b52cb96f3d9c168a6ba8864fdde0d"},{"name":"2020级38班","id":"dc1ac1152b11eab2bf1da6731b254462"},{"name":"2020级39班","id":"ecfb126a43e1f1aea613736bef4b6f35"},{"name":"2020级3班","id":"002ea7a2f6562c486e93cb061af876c8"},{"name":"2020级40班","id":"814891103fd153fe8264bf16523c2732"},{"name":"2020级41班","id":"9f1891acb7346729ba090817a3835ce2"},{"name":"2020级42班","id":"b604ecb60ae8656e1f9791d89057813e"},{"name":"2020级43班","id":"f5f09acd792bff0c6590cde9be548a3e"},{"name":"2020级44班","id":"1aa899b4ab7f24848dce631e437bf051"},{"name":"2020级45班","id":"04630c62fe1b7ab4933f1831072442d1"},{"name":"2020级46班","id":"71f5b9b4291868edec419aafd09925fb"},{"name":"2020级47班","id":"f03c2d9bb0b365c02be350c935781a3b"},{"name":"2020级48班","id":"9690787bd9460a915106c679a592fefd"},{"name":"2020级49班","id":"77de899f242238415c0b56b42d977987"},{"name":"2020级4班","id":"500d945cfc0eac2573c19d39c8e579c3"},{"name":"2020级50班","id":"de2dfb06f84458a1722864fd2c2034ed"},{"name":"2020级5班","id":"9f269f622696c84d24025858ac2d27c7"},{"name":"2020级6班","id":"d685207e9dc1350e540f38a25e8b2aab"},{"name":"2020级7班","id":"cbf3b8f90314c0d608d7b1207756db85"},{"name":"2020级8班","id":"11e0a0e6dc9cbb10d612008cf7c4e32a"},{"name":"2020级9班","id":"0c115438cf73bce4607f7e8931279dcb"}]}
     * msg :
     */




    private HashMap<String, List<Bean>> data;

    public HashMap<String, List<Bean>> getData() {
        return data;
    }

    public void setData(HashMap<String, List<Bean>> data) {
        this.data = data;
    }

    public static class Bean {
        private String name;
        private String id;
        private String gradeId;
        private String hasteacher;

        public String getHasteacher() {
            return hasteacher;
        }

        public void setHasteacher(String hasteacher) {
            this.hasteacher = hasteacher;
        }

        public String getGradeId() {
            return gradeId;
        }

        public void setGradeId(String gradeId) {
            this.gradeId = gradeId;
        }

        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Bean{" +
                    "name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }
    }
}
