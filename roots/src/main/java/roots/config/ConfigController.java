package roots.config;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import roots.core.SystemProperties;

public class ConfigController
{
	private static ConfigController configcontroller;
	private ConfigEntity configentity;
	private File configfile;

	public static final ConfigController getConfigControllerInstance() throws Exception
	{

		if (ConfigController.configcontroller == null)
		{
			ConfigController.configcontroller = new ConfigController();
		}

		return ConfigController.configcontroller;
	}

	private ConfigController() throws Exception
	{
		configfile = new File(SystemProperties.c_config_file);

		this.configentity = new ConfigEntity();

		try
		{

			this.readConfig();

		} catch (Exception e)
		{
			this.saveConfig();
		}
	}

	public ConfigEntity getConfigentity()
	{
		return configentity;
	}

	public void setConfigentity(ConfigEntity configentity)
	{
		this.configentity = configentity;
	}

	private void readConfig() throws Exception
	{
		JAXBContext context = JAXBContext.newInstance(ConfigEntity.class);
		Unmarshaller um = context.createUnmarshaller();
		this.configentity = (ConfigEntity) um.unmarshal(this.configfile);
	}

	public void saveConfig() throws Exception
	{
		JAXBContext context = JAXBContext.newInstance(ConfigEntity.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(this.configentity, this.configfile);
	}

}
